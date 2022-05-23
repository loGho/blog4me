package com.logho.controller.admin;

import com.logho.constants.PageInfo;
import com.logho.constants.UserContants;
import com.logho.pojo.Blog;
import com.logho.pojo.Category;
import com.logho.pojo.LastQueryContent;
import com.logho.pojo.User;
import com.logho.service.admin.AdminBlogService;
import com.logho.service.admin.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/blog")
public class BlogController {


    @Autowired
    private AdminBlogService adminBlogService;

    @Autowired
    private CategoryService categoryService;


    @RequestMapping(value = {"/list", "/list/{pagenum}"})
    public String blogList(Model model, Long categoryId, String title,
                           @PathVariable(value = "pagenum", required = false) Integer pageNum) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRecordTotal(adminBlogService.getBlogNums(categoryId, title));
        pageInfo.setPageNumTotal((int)Math.ceil(pageInfo.getRecordTotal()/(PageInfo.PAGE_SIZE+0.0)));
        pageInfo.setCurNum(Objects.requireNonNullElse(pageNum, 1));
        List<Category> categoryList = categoryService.getCategoryList();
        List<Blog> blogs = adminBlogService.blogList(categoryId, title,PageInfo.PAGE_SIZE*(pageInfo.getCurNum()-1),PageInfo.PAGE_SIZE);
        System.out.println("blogNums"+blogs.size());
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("blogList", blogs);
        LastQueryContent lastQueryContent = new LastQueryContent();
        lastQueryContent.setBlogTitle(title);
        if (categoryId!=null) {
            lastQueryContent.setCategoryId(categoryId);
            lastQueryContent.setCategoryName(categoryService.getCategoryById(categoryId).getCategoryName());
        }
        model.addAttribute("lastQueryContent",lastQueryContent);
        return "admin/blog_manage";
    }

    @GetMapping(value = {"/add", "/add/{id}"})
    public String toAddBlog(@PathVariable(value = "id", required = false) Long blogId,
                             Model model) {
        List<Category> categoryList = categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        if (blogId != null) {
//            System.out.println("logho_for_test");
            Blog blog = adminBlogService.getBlog(blogId);
            blog.setBlogId(blogId);
            blog.setCategoryIds(adminBlogService.convertCategoryIds(blog.getCategories()));
            model.addAttribute("blog",blog);
//            System.out.println(blog);
            return "admin/blog_post";
        }
        Blog blog = new Blog();
        blog.setBlogType(true);
        model.addAttribute("blog",blog);
        return "admin/blog_post";
    }

    @PostMapping(value = {"/save", "/save/{id}"})
    public String saveBlog(@PathVariable(value = "id", required = false) Long blogId,
                           Blog blog, Model model,
                           @SessionAttribute(UserContants.LOGINED_USER) User user) {

        if (blogId!=null) {
            //执行blogService更新的方法
            blog.setUser(user);
            blog.setCategories(categoryService.getAllCategories(blog.getCategoryIds()));
            boolean flag = adminBlogService.updateBlog(blogId, blog);
            return "redirect:/admin/blog/list";
        }
        blog.setCategories(categoryService.getAllCategories(blog.getCategoryIds()));
        adminBlogService.publishBlog(blog, user.getUserId());
        return "redirect:/admin/blog/list";
    }

    @GetMapping("/del/{id}")
    public String delBlog(@PathVariable("id") Long blogId,
                          RedirectAttributes model) {
        boolean flag = adminBlogService.deleteBlog(blogId);
        if (flag) {
            model.addAttribute(UserContants.OPERATION_STATUS, "操作成功");
            return "redirect:/admin/blog/list";
        }
        model.addAttribute(UserContants.OPERATION_STATUS, "操作失败");
        return "redirect:/admin/blog/list";

    }
}
