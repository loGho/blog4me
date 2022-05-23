package com.logho.service;

import com.logho.dao.BlogMapper;
import com.logho.vo.BlogForSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<BlogForSearch> searchBlog(String blogTitle) {
        return blogMapper.getBlogByTitle(blogTitle);
    }
}
