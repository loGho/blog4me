package com.logho.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.logho.constants.UserContants;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BlogForSearch {

    @JsonProperty("blogid")
    private Long blogId;
    @JsonProperty("blogtitle")
    private String blogTitle;
    @JsonProperty("blogdescription")
    private String blogDescription;
    private String blogUrl;

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
        this.blogUrl = UserContants.BLOG_URL +blogId;
    }
}
