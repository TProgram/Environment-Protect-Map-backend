package com.tProgram.epm.dto;

import com.tProgram.epm.mbg.model.CommentInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class CommentInfoAddParam {
    @ApiModelProperty(value = "评论ID")
    private Integer commentId;

    @ApiModelProperty(value = "评论内容")
    private String commentContent;

    @ApiModelProperty(value = "评论内容标题")
    private String title;

    @ApiModelProperty(value = "市区id")
    @NotEmpty(message = "市区id不可为空")
    private Integer cityId;

    @ApiModelProperty(value = "用户id")
    @NotEmpty(message = "用户id不可为空")
    private Integer userId;

    @ApiModelProperty(value = "是否匿名，1匿名，0不匿名")
    private Integer isAnonymous;

    @ApiModelProperty(value = "图片数组")
    private String[] pics;

    public CommentInfo genComment(boolean isReply,String pics) {
        CommentInfo commentInfo = new CommentInfo();

        if (isReply){
            commentInfo.setReplyId(commentId);
        }else {
            commentInfo.setReplyId(0);
        }
        commentInfo.setPics(pics);
        commentInfo.setCityId(cityId);
        commentInfo.setIsAnonymous(isAnonymous);
        commentInfo.setTitle(title);
        commentInfo.setCommentContent(commentContent);
        commentInfo.setUserId(userId);
        return commentInfo;
    }
}
