package com.logho.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {
    private Long userId;
    private String nickname;
    private String username;
    private String password;
    private String userEmail;
    private String userAvatar;
    private Integer userType;
    private Date userCreateTime;
    private Date userUpdateTime;

    private List<Blog> blogList;
}
