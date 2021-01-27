package com.tProgram.epm.dto;

import com.tProgram.epm.mbg.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class CollectListResult {
    List<CommentInfoItem> commentInfoItemList;
    public CollectListResult() {
        this.commentInfoItemList = new ArrayList<>();
    }

    public void mergeUserInfo(Map<Integer, User> userMap) {
        for (CommentInfoItem commentInfoItem : this.commentInfoItemList) {
            User u = userMap.get(commentInfoItem.getUserId());
            if (u != null) {
                commentInfoItem.setUserName(u.getName());
                commentInfoItem.setUserAvatar(u.getAvatar());
            }
        }
    }

    public void mergeCommentInfo(Map<Integer, CommentInfoItem> commentInfoMap) {
        for (CommentInfoItem commentInfoItem : this.commentInfoItemList) {
            CommentInfoItem mapData = commentInfoMap.get(commentInfoItem.getCommentId());
            if (mapData != null) {
                commentInfoItem.setCityId(mapData.getCityId());
                commentInfoItem.setCollect(true);
                commentInfoItem.setCommentContent(mapData.getCommentContent());
                commentInfoItem.setCommentId(mapData.getCommentId());
                commentInfoItem.setCommentTime(mapData.getCommentTime());
                commentInfoItem.setIsAnonymous(mapData.getIsAnonymous());
                commentInfoItem.setPics(mapData.getPics());
                commentInfoItem.setTitle(mapData.getTitle());
                commentInfoItem.setUpdateTime(mapData.getUpdateTime());
                commentInfoItem.setUserId(mapData.getUserId());
            }
        }
    }
}
