package com.logho.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logho.constants.PageInfo;
import com.logho.exception.NotFountException;
import com.logho.service.BlogService;
import com.logho.service.admin.AdminBlogService;
import com.logho.service.admin.CategoryService;
import com.logho.utils.RedisUtils;
import com.logho.vo.BlogCategoryNum;
import com.logho.vo.BlogForSearch;
import com.logho.vo.BlogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class IndexController {

    @Autowired
    private AdminBlogService adminBlogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BlogService blogService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping(value = {"/","/index","/index/{pageNum}"})
    public String toIndex(@PathVariable(value = "pageNum",required = false) Integer pageNum,
                          Model model) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRecordTotal(adminBlogService.getBlogNums(null,null));
        pageInfo.setPageNumTotal((int)Math.ceil(pageInfo.getRecordTotal()/(PageInfo.PAGE_SIZE+0.0)));
        pageInfo.setCurNum(Objects.requireNonNullElse(pageNum, 1));
        List<BlogVO> blogs = adminBlogService.lastBlogList(PageInfo.PAGE_SIZE*(pageInfo.getCurNum()-1));
//        for (BlogVO blog : blogs) {
//            String blogPublishTime = blog.getBlogUpdateTime()==null ? blog.getBlogCreateTime().toString():blog.getBlogUpdateTime().toString();
////            System.out.println(blog.getBlogAvatar());
//            if ((redisTemplate.opsForValue().get(RedisUtils.getRedisBlogTitleKey(blog.getBlogId())))==null) {
//                redisTemplate.opsForValue().set(RedisUtils.getRedisBlogTitleKey(blog.getBlogId()), blog.getBlogTitle());
//                redisTemplate.opsForValue().set(RedisUtils.getRedisBlogContentKey(blog.getBlogId()), blog.getBlogContent());
//                redisTemplate.opsForValue().set(RedisUtils.getRedisBlogAuthorKey(blog.getBlogId()), blog.getBlogAuthor());
//                redisTemplate.opsForValue().set(RedisUtils.getRedisBlogAvatarKey(blog.getBlogId()), blog.getBlogAvatar());
//                redisTemplate.opsForValue().set(RedisUtils.getRedisBlogPublishTimeKey(blog.getBlogId()),blogPublishTime );
//            }
//        }
        RedisUtils.setBlogToRedis(blogs,redisTemplate);
        model.addAttribute("lastBlogList",blogs);
        return "index";
    }



    @GetMapping("/category")
    public String toCategory(Model model) {
        List<BlogCategoryNum> categoryBlogNumList = categoryService.getEachBlogNum();
        model.addAttribute("categoryBlogNum",categoryBlogNumList);
        return "category";
    }



    @GetMapping(value = {"/category/blogs/{categoryId}/{curPage}","/category/blogs/{categoryId}"})
    public String getCategoryBlog(Model model,
                                  @PathVariable("categoryId") Long categoryId,
                                  @PathVariable(value = "curPage",required = false) Integer pageNum) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRecordTotal(adminBlogService.getBlogNums(categoryId,null));
        pageInfo.setPageNumTotal((int)Math.ceil(pageInfo.getRecordTotal()/(PageInfo.PAGE_SIZE-3.0)));
        pageInfo.setCurNum(Objects.requireNonNullElse(pageNum, 1));
        List<BlogCategoryNum> categoryBlogNumList = categoryService.getEachBlogNum();
        model.addAttribute("categoryBlogNum",categoryBlogNumList);
        List<BlogVO> blogByCategory = categoryService.getBlogByCategory(categoryId,PageInfo.PAGE_SIZE*(pageInfo.getCurNum()-1));
        RedisUtils.setBlogToRedis(blogByCategory,redisTemplate);
        model.addAttribute("categoryBlog",blogByCategory);
        return "category";
    }

    @GetMapping("blog/search")
    @ResponseBody
    public String blogSearch(@RequestParam("blogtitle")  String blogTitle) {
        List<BlogForSearch> blogList = blogService.searchBlog(blogTitle);
//        System.out.println("blogList = " + blogList);
        ObjectMapper objectMapper = new ObjectMapper();
        String blogs = null;
        try {
           blogs = objectMapper.writeValueAsString(blogList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new NotFountException();
        }
        return "{ \"items\":"+blogs+" }";

    }

    @GetMapping("/admin/blog-admin")
    public String toAdmin() {
        return "admin/blog_post";
    }
    @GetMapping("/about")
    public String aboutMe() {
        return "about";
    }
    @GetMapping("/blog/manage")
    public String manageBlog() {
        return "admin/blog_manage";
    }
    @GetMapping("/blog/login")
    public String adminLogin() {
        HashMap<String,Integer> resultMap = new HashMap<>();
        ArrayList<Map.Entry<String,Integer>> mapList = new ArrayList<>(resultMap.entrySet());
        mapList.sort((o1, o2) -> o2.getValue() - o1.getValue());
            

        return "admin/login";
    }
    @GetMapping("/center")
    public String testCenter() {
        return "center";
    }
    @GetMapping("/login/login")
    public String testIndex() {
        return "login";
    }

    @GetMapping("/redis")
    @ResponseBody
    public String testRedis() {

        redisTemplate.opsForValue().set("helloworld","thanks");
        redisTemplate.opsForValue().set("我是谁","thanks");
        Object hello = redisTemplate.opsForValue().get("helloworld");
        assert hello != null;
        return hello.toString();
    }


}
