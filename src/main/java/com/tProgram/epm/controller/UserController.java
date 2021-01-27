package com.tProgram.epm.controller;

import com.tProgram.epm.common.api.CommonResult;
import com.tProgram.epm.dto.LoginInfo;
import com.tProgram.epm.dto.RegisterParam;
import com.tProgram.epm.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Description
 * <p>
 *     我的相关控制器
 * </p>
 * DATE 2020/9/1.
 *
 * @author genglintong.
 */
@Controller
@Api(tags = "UserController", description = "用户相关")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 登录
     */
    @ApiOperation(value = "微信小程序登陆")
    @PostMapping("login")
    @ResponseBody
    public CommonResult login(@RequestBody LoginInfo loginInfo) {
        String token = userService.login(loginInfo.getCode());

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    /**
     * 微信小程序注册
     */
    @ApiOperation(value = "微信小程序注册")
    @PostMapping("register")
    @ResponseBody
    public CommonResult register(@RequestBody RegisterParam param) {
        String token = userService.register(param);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

}
