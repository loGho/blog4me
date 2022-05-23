package com.logho.dao;

import com.logho.vo.BlogForSearch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper {

    List<BlogForSearch> getBlogByTitle(String blogTitle);
}
