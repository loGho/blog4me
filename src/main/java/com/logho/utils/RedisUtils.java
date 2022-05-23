package com.logho.utils;

import com.logho.vo.BlogVO;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

public class RedisUtils {


    public static String getRedisBlogContentKey(Long blogId) {
        return "blog:"+blogId+"content";
    }
    public static String getRedisBlogTitleKey(Long blogId) {
        return "blog:"+blogId+"title";
    }
    public static String getRedisBlogAuthorKey(Long blogId) {
        return "blog:"+blogId+"author";
    }
    public static String getRedisBlogPublishTimeKey(Long blogId) {
        return "blog:"+blogId+"publishTime";
    }
    public static String getRedisBlogAvatarKey(Long blogId) {
        return "blog:"+blogId+"avatar";
    }

    public static void setBlogToRedis(List<BlogVO> blogs,RedisTemplate<String,String> redisTemplate) {
        for (BlogVO blog : blogs) {
            String blogPublishTime = blog.getBlogUpdateTime()==null ? blog.getBlogCreateTime().toString():blog.getBlogUpdateTime().toString();
//            System.out.println(blog.getBlogAvatar());
            if ((redisTemplate.opsForValue().get(RedisUtils.getRedisBlogTitleKey(blog.getBlogId())))==null) {
                redisTemplate.opsForValue().set(RedisUtils.getRedisBlogTitleKey(blog.getBlogId()), blog.getBlogTitle());
                redisTemplate.opsForValue().set(RedisUtils.getRedisBlogContentKey(blog.getBlogId()), blog.getBlogContent());
                redisTemplate.opsForValue().set(RedisUtils.getRedisBlogAuthorKey(blog.getBlogId()), blog.getBlogAuthor());
                redisTemplate.opsForValue().set(RedisUtils.getRedisBlogAvatarKey(blog.getBlogId()), blog.getBlogAvatar());
                redisTemplate.opsForValue().set(RedisUtils.getRedisBlogPublishTimeKey(blog.getBlogId()),blogPublishTime );
            }
        }
    }
}
