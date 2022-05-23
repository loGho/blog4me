package com.logho.service;

import com.logho.vo.BlogForSearch;

import java.util.List;

public interface BlogService {

    List<BlogForSearch> searchBlog(String blogTitle);
}
