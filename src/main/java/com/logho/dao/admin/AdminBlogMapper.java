package com.logho.dao.admin;

import com.logho.pojo.Blog;
import com.logho.vo.BlogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminBlogMapper {

    Integer getBlogCount(Long categoryId, String blogTitle);

    List<Blog> getBlogList(Long categoryId, String blogTitle,int pageSize);

    List<Blog> getBlogListFinal(@Param("categoryId") Long categoryId, String blogTitle, int pageSize);

    List<Blog> getBlogList(Long categoryId, String blogTitle,int startIndex,int pageSize);

    List<BlogVO> getLastBlogList(Integer startPage);

    boolean updateBlogById(Long blogId,Blog blog);

    boolean addBlog(Blog blog,Long userId);

    Blog getBlogById(Long blogId);

    boolean removeBlogById(Long blogId);

    boolean addBlogCategories(Long blogId,Long categoryId);
}
