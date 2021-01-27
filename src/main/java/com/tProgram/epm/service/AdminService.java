package com.tProgram.epm.service;

import com.tProgram.epm.mbg.model.UserAdmin;

/**
 * 后台管理员Service
 * Created by macro on 2018/4/26.
 */
public interface AdminService {

    UserAdmin getAdminByUsername(String username);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username,String password);

}