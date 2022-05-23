package com.logho.dao.admin;

import com.logho.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    User queryUserByUsernameAndPassword(String username,String password);
    boolean getUsername(String username);
    boolean insertUser(User user);
}
