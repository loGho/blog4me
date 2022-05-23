package com.logho.controller;

import com.logho.exception.NotFountException;
import com.logho.service.admin.AdminBlogService;
import com.logho.service.admin.CategoryService;
import com.logho.utils.MarkdownUtils;
import com.logho.utils.RedisUtils;
import com.logho.vo.BlogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class BlogViewController {
    @Autowired
    private AdminBlogService adminBlogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @PostMapping(value = {"/blog/{blogTitle}"})
    public String blogDetails(Long blogId,
                              Model model) {
        BlogDTO blog = new BlogDTO();
        blog.setBlogId(blogId);
        blog.setBlogAuthor(redisTemplate.opsForValue().get(RedisUtils.getRedisBlogAuthorKey(blogId)));
        blog.setBlogContent(MarkdownUtils.markdownToHtmlExtensions(redisTemplate.opsForValue().get(RedisUtils.getRedisBlogContentKey(blogId))));
        blog.setBlogTitle(redisTemplate.opsForValue().get(RedisUtils.getRedisBlogTitleKey(blogId)));
        model.addAttribute("blog",blog);
//        System.out.println(MarkdownUtils.markdownToHtmlExtensions(redisTemplate.opsForValue().get(RedisStringUtils.getRedisBlogContentKey(blogId))));
        return "blog_details";
    }
    @GetMapping(value = {"/blog/{id}"})
    public String blogDetailsByGet(@PathVariable("id") Long blogId, Model model) {
        BlogDTO blog = new BlogDTO();
        if (redisTemplate.opsForValue().get(RedisUtils.getRedisBlogTitleKey(blogId))!=null) {
            blog.setBlogId(blogId);
            blog.setBlogAuthor(redisTemplate.opsForValue().get(RedisUtils.getRedisBlogAuthorKey(blogId)));
            blog.setBlogContent(MarkdownUtils.markdownToHtmlExtensions(redisTemplate.opsForValue().get(RedisUtils.getRedisBlogContentKey(blogId))));
            blog.setBlogTitle(redisTemplate.opsForValue().get(RedisUtils.getRedisBlogTitleKey(blogId)));
            blog.setBlogAuthor(redisTemplate.opsForValue().get(RedisUtils.getRedisBlogAuthorKey(blogId)));
            blog.setBlogAvatar(redisTemplate.opsForValue().get(RedisUtils.getRedisBlogAvatarKey(blogId)));
            blog.setBlogPublishTime(redisTemplate.opsForValue().get(RedisUtils.getRedisBlogPublishTimeKey(blogId)));
            blog.setCategories(categoryService.getCategoryByBLogId(blogId));
//        System.out.println(redisTemplate.opsForValue().get(RedisStringUtils.getRedisBlogAvatarKey(blogId)));
            model.addAttribute("blog", blog);
//        System.out.println(MarkdownUtils.markdownToHtmlExtensions(redisTemplate.opsForValue().get(RedisStringUtils.getRedisBlogContentKey(blogId))));
            return "blog_details";
        } else {
            throw new NotFountException();
        }

    }
}
