package com.logho.service;

import com.logho.dao.admin.UserMapper;
import com.logho.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean checkUsername(String username) {
       return userMapper.getUsername(username);
    }

    @Override
    public User getUser(String username, String password) {
        User user = userMapper.queryUserByUsernameAndPassword(username, DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
        return user;
    }

    @Override
    public boolean addUser(User user) {
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        return userMapper.insertUser(user);
    }
}
