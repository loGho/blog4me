package com.logho.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Blog {

    private Long blogId;
    private String blogTitle;
    private String blogShowImg;
    private String blogContent;
    private String blogDescription;
    private Integer blogViews;
    private Integer blogLike;
    private Boolean blogPublishStatus;
    private Boolean enableComments;
    private Boolean isRecommend;
    private Date blogCreateTime;
    private Date blogUpdateTime;

    private List<Category> categories;
    private Boolean blogType;
    private User user;
    private List<Comment> commentList;
    private String categoryIds;
}
