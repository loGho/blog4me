package com.logho.service.admin;

import com.logho.pojo.Blog;
import com.logho.pojo.Category;
import com.logho.vo.BlogVO;

import java.util.List;


public interface AdminBlogService {

    Blog getBlog(Long blogId);

    Integer getBlogNums(Long categoryId, String blogTitle);

    List<Blog> blogList(Long categoryId,String blogTitle,int startIndex, int pageSize);

    List<BlogVO> lastBlogList(int startPage);

    boolean publishBlog(Blog blog,Long userId);

    boolean updateBlog(Long id,Blog blog);

    boolean deleteBlog(Long blogId);


    String convertCategoryIds(List<Category> categories);
}
