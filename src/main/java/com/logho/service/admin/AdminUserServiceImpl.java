package com.logho.service.admin;

import com.logho.dao.admin.UserMapper;
import com.logho.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser(String username, String password) {
        User user = userMapper.queryUserByUsernameAndPassword(username, DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
        return user;
    }
}
