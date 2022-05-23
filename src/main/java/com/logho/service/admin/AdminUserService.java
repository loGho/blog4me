package com.logho.service.admin;

import com.logho.pojo.User;

public interface AdminUserService {
    User getUser(String username,String password);
}
