package com.logho.service;

import com.logho.pojo.User;

public interface UserService {
    boolean checkUsername(String username);
    User getUser(String username, String password);
    boolean addUser(User user);
}
