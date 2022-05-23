package com.logho.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Comment {

    private Long commentId;
    private String commentContent;
    private String nickName;
    private String userEmail;
    private String userAvatar;
    private Date commentCreateTime;

    private Blog blog;
    private List<Comment> commentList;
    private Comment commentParent;

}
