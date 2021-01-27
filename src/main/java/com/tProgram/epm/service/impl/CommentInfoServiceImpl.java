package com.tProgram.epm.service.impl;

import com.tProgram.epm.dto.CollectListResult;
import com.tProgram.epm.dto.CommentInfoAddParam;
import com.tProgram.epm.dto.CommentInfoItem;
import com.tProgram.epm.dto.CommentInfoListResult;
import com.tProgram.epm.mbg.mapper.CollectMapper;
import com.tProgram.epm.mbg.mapper.CommentInfoMapper;
import com.tProgram.epm.mbg.model.*;
import com.tProgram.epm.service.CommentInfoService;
import com.tProgram.epm.service.UserService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class CommentInfoServiceImpl implements CommentInfoService {
    @Autowired
    private CommentInfoMapper commentInfoMapper;

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private UserService userService;

    @Override
    public CommentInfoListResult all_list(Integer cityId) {
        CommentInfoExample example = new CommentInfoExample();

        example.setOrderByClause("update_time desc");
        example.createCriteria().andCityIdEqualTo(cityId).andReplyIdEqualTo(0);

        List<CommentInfo> list = commentInfoMapper.selectByExampleWithBLOBs(example);
        CommentInfoListResult res = new CommentInfoListResult();

        List<Integer> userIDs = new ArrayList<>();
        for (CommentInfo commentInfo : list) {
            userIDs.add(commentInfo.getUserId());
            res.getCommentInfoItemList().add(new CommentInfoItem(commentInfo));
        }

        // 3. 补充用户信息
        Map<Integer, User> users = userService.batchUser(userIDs);
        res.mergeUserInfo(users);
        return res;
    }

    @Override
    public boolean add_comment(CommentInfoAddParam commentInfo){
        boolean isReply = false;

        User user = userService.getCurrentUser();
        commentInfo.setUserId(user.getId());

        // 确定是评论还是回复，回复有个comment_id。
        if (commentInfo.getCommentId() != null ){
            isReply = true;
            CommentInfo updateCommentTime = new CommentInfo();
            updateCommentTime.setId(commentInfo.getCommentId());
            updateCommentTime.setUpdateTime(new Date());
            commentInfoMapper.updateByPrimaryKeySelective(updateCommentTime);
        }

        String picsString = JSONArray.fromObject(commentInfo.getPics()).toString();

        CommentInfo model = commentInfo.genComment(isReply,picsString);
        commentInfoMapper.insertSelective(model);
        return true;
    }

    @Override
    public CommentInfoListResult detail(Integer commentId) {
        CommentInfoExample example = new CommentInfoExample();
        example.createCriteria().andIdEqualTo(commentId);
        List<CommentInfo> list = commentInfoMapper.selectByExampleWithBLOBs(example);

        // 回复数据
        CommentInfoExample exampleReply = new CommentInfoExample();
        exampleReply.createCriteria().andReplyIdEqualTo(commentId);
        List<CommentInfo> listReply = commentInfoMapper.selectByExampleWithBLOBs(exampleReply);

        CommentInfoListResult listResult = new CommentInfoListResult();
        List<Integer> userIDs = new ArrayList<>();

        //评论人的信息单独处理
        listResult.getCommentInfoItemList().add(new CommentInfoItem(list.get(0)));//评论内容
        // 检查我是否收藏了这家伙
        User myself = null;
        try {
            myself = userService.getCurrentUser();//得先知道我是谁
        }catch (Exception ignored) {
            // 用户未登陆 正常情况
        }

        // 此处没有加入登陆鉴权 直接加个判空 默认
        List<Collect> list1 = null;
        if (myself != null) {
            CollectExample collectExample = new CollectExample();
            collectExample.createCriteria().andUserIdEqualTo(myself.getId()).andCommentIdEqualTo(commentId);
            list1 = collectMapper.selectByExample(collectExample);
        }

        if (list1 == null || list1.size() == 0){
            listResult.getCommentInfoItemList().get(0).setCollect(false);
        }else {
            listResult.getCommentInfoItemList().get(0).setCollect(true);
            SimpleDateFormat dateToString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = dateToString.format(list1.get(0).getUpdateTime());
            listResult.getCommentInfoItemList().get(0).setCollectTime(time);
        }

        //发帖人的信息预备
        userIDs.add(list.get(0).getUserId());

        //回复信息
        for (CommentInfo replyInfo : listReply){
            userIDs.add(replyInfo.getUserId());
            listResult.getCommentInfoItemList().add(new CommentInfoItem(replyInfo));
        }

        // 补充用户名
        Map<Integer, User> users = userService.batchUser(userIDs);
        listResult.mergeUserInfo(users);
        return listResult;
    }

    @Override
    public CommentInfoListResult latest() {
        CommentInfoExample example = new CommentInfoExample();

        example.setOrderByClause("update_time desc limit 5");
        example.createCriteria().andReplyIdEqualTo(0);

        List<CommentInfo> list = commentInfoMapper.selectByExampleWithBLOBs(example);
        CommentInfoListResult res = new CommentInfoListResult();

        List<Integer> userIDs = new ArrayList<>();
        for (CommentInfo commentInfo : list) {
            userIDs.add(commentInfo.getUserId());
            res.getCommentInfoItemList().add(new CommentInfoItem(commentInfo));
        }

        // 3. 补充用户信息
        Map<Integer, User> users = userService.batchUser(userIDs);
        res.mergeUserInfo(users);
        return res;
    }

    @Override
    public boolean collect(Integer commentId,boolean isCollect){
        User user = userService.getCurrentUser();
        CollectExample collectExample = new CollectExample();
        Collect model = new Collect();

        model.setUserId(user.getId());
        model.setCommentId(commentId);

        if (isCollect){
            model.setUpdateTime(new Date());
            collectMapper.insert(model);
        }else {
            collectExample.createCriteria().andCommentIdEqualTo(commentId).andUserIdEqualTo(user.getId());
            collectMapper.deleteByExample(collectExample);
        }
        return true;
    }

    @Override
    public CollectListResult collect_list(){
        User user = userService.getCurrentUser();
        CollectExample collectExample = new CollectExample();
        collectExample.setOrderByClause("update_time desc");
        collectExample.createCriteria().andUserIdEqualTo(user.getId());
        List<Collect> list = collectMapper.selectByExample(collectExample);

        CollectListResult res = new CollectListResult();

        List<Integer> userIDs = new ArrayList<>();
        List<Integer> commentIDs = new ArrayList<>();

        for (Collect collect : list){
            userIDs.add(collect.getUserId());
            commentIDs.add(collect.getCommentId());
            res.getCommentInfoItemList().add(new CommentInfoItem(collect));
        }
        // 补充帖子数据
        Map<Integer, CommentInfoItem> commentInfoItemMap = batchCommentInfoItem(commentIDs);
        res.mergeCommentInfo(commentInfoItemMap);

        // 补充用户名
        Map<Integer, User> users = userService.batchUser(userIDs);
        res.mergeUserInfo(users);

        return res;
    }

    @Override
    public Map<Integer, CommentInfoItem> batchCommentInfoItem(List<Integer> commentIDs){
        if (commentIDs.isEmpty()) {
            return new HashMap<>();
        }
        CommentInfoExample example = new CommentInfoExample();
        example.createCriteria().andIdIn(commentIDs);
        List<CommentInfo> commentInfos = commentInfoMapper.selectByExampleWithBLOBs(example);

        List<CommentInfoItem> res = new ArrayList<CommentInfoItem>();
        for (CommentInfo commentInfo : commentInfos){
            res.add(new CommentInfoItem(commentInfo));
        }
        return res.stream().collect(Collectors.toMap(CommentInfoItem::getCommentId, a -> a,(k1, k2)->k1));
    }
}
