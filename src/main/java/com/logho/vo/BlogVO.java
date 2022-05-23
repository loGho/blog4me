package com.logho.vo;

import com.logho.utils.PinYinTransUtils;
import lombok.Data;

import java.util.Date;

@Data
public class BlogVO {
    private Long blogId;
    private String blogTitle;
    private String blogShowImg;
    private String blogContent;
    private String blogDescription;
    private Integer blogViews;
    private Date blogUpdateTime;
    private Date blogCreateTime;
    private String blogShowTitle;
    private String blogAuthor;
    private String blogAvatar;

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
        this.blogShowTitle = PinYinTransUtils.getTitlePinYin(blogTitle);
    }
}
