package com.tProgram.epm.dto;

import com.tProgram.epm.mbg.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Data
public class CommentInfoListResult {
    List<CommentInfoItem> commentInfoItemList;
    public CommentInfoListResult() {
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
}
