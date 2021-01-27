package com.tProgram.epm.service;

import com.tProgram.epm.dto.CollectListResult;
import com.tProgram.epm.dto.CommentInfoAddParam;
import com.tProgram.epm.dto.CommentInfoItem;
import com.tProgram.epm.dto.CommentInfoListResult;

import java.util.List;
import java.util.Map;

/*
评论逻辑层
 */
public interface CommentInfoService {
    //返回相应地区讨论数据
    CommentInfoListResult all_list(Integer cityId);

    boolean add_comment(CommentInfoAddParam commentInfo);

    CommentInfoListResult detail(Integer commentId);

    CommentInfoListResult latest();

    boolean collect(Integer commentId,boolean isCollect);

    CollectListResult collect_list();

    Map<Integer, CommentInfoItem> batchCommentInfoItem(List<Integer> commentIDs);
}
