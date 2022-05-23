package com.logho.service.admin;

import com.logho.dao.admin.AdminBlogMapper;
import com.logho.dao.admin.CategoryMapper;
import com.logho.pojo.Blog;
import com.logho.pojo.Category;
import com.logho.vo.BlogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdminBlogServiceImpl implements AdminBlogService {

    @Autowired
    private AdminBlogMapper adminBlogMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Blog getBlog(Long blogId) {
        return adminBlogMapper.getBlogById(blogId);
    }

    @Override
    public Integer getBlogNums(Long categoryId, String blogTitle) {
       return adminBlogMapper.getBlogCount(categoryId,blogTitle);
    }

    @Override
    public List<Blog> blogList(Long categoryId, String blogTitle,int startIndex,int pageSize) {
        List<Blog> blogList = adminBlogMapper.getBlogList(categoryId, blogTitle,startIndex,pageSize);
        return blogList;
    }

    @Override
    public List<BlogVO> lastBlogList(int startPage) {
        List<BlogVO> lastBlogList = adminBlogMapper.getLastBlogList(startPage);
        return lastBlogList;
    }

    @Override
    public boolean publishBlog(Blog blog, Long userId) {
        blog.setBlogCreateTime(new Date());
        blog.setBlogUpdateTime(new Date());
        //把blog写入数据库
        boolean flag = adminBlogMapper.addBlog(blog, userId);
        //把blog和category对应关系写入数据库
        addAllCategories(blog);
        return flag;
    }


    @Override
    public boolean updateBlog(Long blogId, Blog blog) {
        boolean flag = false;
        blog.setBlogUpdateTime(new Date());
        blog.setBlogId(blogId);
        categoryMapper.deleteAllCategoryByBlogId(blogId);
        flag =  adminBlogMapper.updateBlogById(blogId, blog);
        addAllCategories(blog);
        return flag;
    }

    @Override
    public boolean deleteBlog(Long blogId) {
        boolean flag = false;
        flag = adminBlogMapper.removeBlogById(blogId);
        return flag;
    }

    public void addAllCategories(Blog blog) {
//        System.out.println("=======");
//        System.out.println(blog.getCategories());
        if (blog.getCategories() != null) {
            for (Category category : blog.getCategories()) {
//                System.out.println("========================="+category);
//                blogId为null,没有获取blogId，通过Mybatis的usegeneratorKey
//                System.out.println("blogid"+blog.getBlogId());
//                System.out.println("categoryid" + category.getCategoryId());
                adminBlogMapper.addBlogCategories(blog.getBlogId(), category.getCategoryId());
            }
        }
    }

    @Override
    public String convertCategoryIds(List<Category> categories) {
        StringBuffer categoryIds = new StringBuffer();
        for (Category category : categories) {
            categoryIds.append(category.getCategoryId());
            categoryIds.append(',');
        }
        if (categoryIds.length()!=0) {
            categoryIds.deleteCharAt(categoryIds.length() - 1);
        }
        return categoryIds.toString();
    }
}
