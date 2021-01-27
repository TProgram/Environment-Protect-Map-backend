package com.tProgram.epm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tProgram.epm.common.utils.JwtTokenUtil;
import com.tProgram.epm.dto.AdminUserDetails;
import com.tProgram.epm.dto.RegisterParam;
import com.tProgram.epm.exception.Asserts;
import com.tProgram.epm.mbg.mapper.UserMapper;
import com.tProgram.epm.mbg.model.User;
import com.tProgram.epm.mbg.model.UserExample;
import com.tProgram.epm.service.UserService;
import com.tProgram.epm.util.wechat.Decrypt;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * UmsAdminService实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${wx.webAccessTokenHttps}")
    private String webAccessTokenHttps;
    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.secret}")
    private String secret;
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getAdminByUserID(int userID) {
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(userID);
        List<User> adminList = userMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }

    @Override
    public String register(RegisterParam param) {
        // 1. 获取登陆信息
        JSONObject sessionData = getWXLoginInfo(param.getCode());
        String sessionKey = sessionData.getString("session_key");

        // 2. 数据解密
        String res = Decrypt.decrypt(this.appId, param.getEncryptedData(), sessionKey, param.getIv());
        if (Strings.isBlank(res)) {
            Asserts.fail("用户数据解密失败");
        }
        JSONObject userInfo = JSON.parseObject(res);

        // 3. 用户注册数据写入
        User user = new User();
        user.setAvatar(userInfo.getString("avatarUrl"));
        user.setInfo(res);
        user.setOpenid(userInfo.getString("openId"));
        user.setName(userInfo.getString("nickName"));
        Integer userID = userMapper.insertSelective(user);

        // 4. 登陆态写入
        String token = null;
        try {
            user.setId(userID);
            UserDetails userDetails = new AdminUserDetails(user);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    @Override
    public User getCurrentUser() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        AdminUserDetails memberDetails = (AdminUserDetails) auth.getPrincipal();
        return memberDetails.getUser();
    }

    @Override
    public String login(String code) {
        //1. 获取openid
        JSONObject sessionData = getWXLoginInfo(code);
        String openid = sessionData.getString("openid");

        // 2. 根据openid 查找用户数据
        UserExample example = new UserExample();
        example.createCriteria().andOpenidEqualTo(openid);
        List<User> users = userMapper.selectByExample(example);

        if (users.size() == 0) {
            // todo 用户未注册
            Asserts.fail("用户未注册");
        }
        String token = null;
        try {
            User user = users.get(0);
            UserDetails userDetails = new AdminUserDetails(user);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }


    private JSONObject getWXLoginInfo(String code) {
        String requestUrl = String.format(this.webAccessTokenHttps, this.appId, this.secret, code);

        String res = restTemplate.getForObject(requestUrl, String.class);
        return JSON.parseObject(res);
    }

    @Override
    public Map<Integer, User> batchUser(List<Integer> userIDs) {
        if (userIDs.isEmpty()) {
            return new HashMap<>();
        }
        UserExample example = new UserExample();
        example.createCriteria().andIdIn(userIDs);
        List<User> users = userMapper.selectByExample(example);

        return users.stream().collect(Collectors.toMap(User::getId, a -> a,(k1, k2)->k1));
    }
}
