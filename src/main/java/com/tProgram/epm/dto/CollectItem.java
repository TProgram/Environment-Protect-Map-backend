package com.tProgram.epm.dto;

import com.alibaba.fastjson.JSON;
import com.tProgram.epm.mbg.model.CommentInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Data
public class CollectItem {
    @ApiModelProperty(value = "评论ID")
    private Integer commentId;

    @ApiModelProperty(value = "评论标题")
    private String title;

    @ApiModelProperty(value = "评论内容")
    private String commentContent;

    @ApiModelProperty(value = "市区名字")
    private Integer cityId;

    @ApiModelProperty(value = "是否匿名")
    private Integer isAnonymous;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户头像")
    private String userAvatar;

    @ApiModelProperty(value = "评论时间")
    private String commentTime;

    @ApiModelProperty(value = "最新回复时间")
    private String updateTime;

    @ApiModelProperty(value = "是否收藏")
    private boolean isCollect;

    @ApiModelProperty(value = "图片数组")
    private List<String> pics;


    public CollectItem(CommentInfo commentInfo) {
        this.commentId = commentInfo.getId();
        this.title = commentInfo.getTitle();
        this.commentContent = commentInfo.getCommentContent();
        SimpleDateFormat dateToString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.commentTime = dateToString.format(commentInfo.getCommentTime());
        this.updateTime = dateToString.format(commentInfo.getUpdateTime());
        this.isAnonymous = commentInfo.getIsAnonymous();
        this.userId = commentInfo.getUserId();
        this.cityId = commentInfo.getCityId();
        this.userName = "";
        this.userAvatar = "";
        this.isCollect = false;

        if ( !commentInfo.getPics().equals("")){
            String picsJsonString = commentInfo.getPics();
            JSONArray picsJsonArr = JSONArray.fromObject(picsJsonString);
            List<String> picsStringList = JSON.parseArray(picsJsonArr.toString(),String.class);
            this.pics = picsStringList;
        }else {
            this.pics = new ArrayList<String>();
        }
    }
}
