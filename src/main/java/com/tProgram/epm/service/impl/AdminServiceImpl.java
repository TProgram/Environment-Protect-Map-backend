package com.tProgram.epm.service.impl;

import com.tProgram.epm.dto.AdminUserDetails;
import com.tProgram.epm.mbg.mapper.UserAdminMapper;
import com.tProgram.epm.mbg.model.*;
import com.tProgram.epm.common.utils.JwtTokenUtil;
import com.tProgram.epm.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UmsAdminService实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserAdminMapper userAdminMapper;

    @Override
    public UserAdmin getAdminByUsername(String username) {
        UserAdminExample example = new UserAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UserAdmin> adminList = userAdminMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }

    /*用户登录*/
    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
//            UserDetails userDetails = loadUserByUsername(username);
//            if(!passwordEncoder.matches(password,userDetails.getPassword())){
//                throw new BadCredentialsException("密码不正确");
//            }
            User user = new User();
            user.setId(2);
            UserDetails userDetails = new AdminUserDetails(user);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    public UserDetails loadUserByUsername(String username){
        //获取用户信息
        UserAdmin admin = getAdminByUsername(username);
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
