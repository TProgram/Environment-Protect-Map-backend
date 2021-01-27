package com.tProgram.epm.service;

import com.tProgram.epm.dto.RegisterParam;
import com.tProgram.epm.mbg.model.User;

import java.util.List;
import java.util.Map;

/**
 * 用户相关Service
 * add by lintong
 */
public interface UserService {
    /**
     * 根据用户ID 获取用户基本信息
     */
    User getAdminByUserID(int userID);

    /**
     * 注册功能
     * @param param
     */
    String register(RegisterParam param);

    /**
     * 获取当前用户信息
     * @return
     */
    User getCurrentUser();

    /**
     * 登录功能
     * @param code 微信code
     * @return 生成的JWT的token
     */
    String login(String code);


    Map<Integer, User> batchUser(List<Integer> userIDs);
}
