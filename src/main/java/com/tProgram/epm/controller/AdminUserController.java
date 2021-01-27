package com.tProgram.epm.controller;

import com.tProgram.epm.common.api.CommonResult;
import com.tProgram.epm.dto.UserAdminLoginParam;
import com.tProgram.epm.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Description
 * <p>
 *     后台管理系统登录
 * </p>
 * DATE 2020/9/17.
 *
 * @author Echo.
 */
@Controller
@Api(tags = "AdminUserController", description = "后台用户管理")
@RequestMapping("/admin")
public class AdminUserController {
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "登录且返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UserAdminLoginParam userAdminLoginParam, BindingResult result) {
        String token = adminService.login(userAdminLoginParam.getUsername(), userAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }
}
