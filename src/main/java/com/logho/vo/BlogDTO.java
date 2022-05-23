package com.logho.vo;

import com.logho.pojo.Category;
import lombok.Data;

import java.util.List;

@Data
public class BlogDTO {
    private Long blogId;
    private String blogTitle;
    private String blogContent;
    private String blogAuthor;
    private String blogPublishTime;
    private String blogAvatar;
    private List<Category> categories;


}
