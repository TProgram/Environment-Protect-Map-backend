package com.tProgram.epm.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class CommentInfo implements Serializable {
    @ApiModelProperty(value = "评论或回复的id，回复有专门的reply_id值")
    private Integer id;

    @ApiModelProperty(value = "回复评论的id，若为评论则取0，若是回复则取对应的comment_id")
    private Integer replyId;

    @ApiModelProperty(value = "评论地区")
    private Integer cityId;

    @ApiModelProperty(value = "用于join user表，获取头像，姓名")
    private Integer userId;

    private Date commentTime;

    private Date updateTime;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "0不匿名，1匿名")
    private Integer isAnonymous;

    @ApiModelProperty(value = "图片数组")
    private String pics;

    private String commentContent;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Integer isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", replyId=").append(replyId);
        sb.append(", cityId=").append(cityId);
        sb.append(", userId=").append(userId);
        sb.append(", commentTime=").append(commentTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", title=").append(title);
        sb.append(", isAnonymous=").append(isAnonymous);
        sb.append(", pics=").append(pics);
        sb.append(", commentContent=").append(commentContent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}