package com.logho;

import com.github.houbb.pinyin.constant.enums.PinyinStyleEnum;
import com.github.houbb.pinyin.util.PinyinHelper;
import com.logho.dao.admin.AdminBlogMapper;
import com.logho.dao.admin.CategoryMapper;
import com.logho.pojo.Blog;
import com.logho.pojo.Category;
import com.logho.service.admin.AdminBlogService;
import com.logho.service.admin.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@SpringBootTest
class Blog4meApplicationTests {

    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    AdminBlogMapper adminBlogMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    AdminBlogService adminBlogService;

    @Test
    void contextLoads() {
        boolean b = categoryMapper.addCategory("2471133171");
        System.out.println("b = " + b);
        Category categoryByName = categoryMapper.getCategoryByName("1241");
        System.out.println(categoryByName == null);
    }

//    @Test
//    void test1() {
//        String[] subDomains = "google.com".split("\\.");
//        for (String subDomain : subDomains) {
//            System.out.println("subDomain = " + subDomain);
//        }
//        String[] split = "com".split("//.");
//        System.out.println(split.length);
//        for (String s : split) {
//            System.out.println("s = " + s);
//        }
//
//    }
//
//    @Test
//    void testQueryBlog() {
//        List<Blog> blogList = blogMapper.getBlogList(null, "mysql", 8);
//        for (Blog blog : blogList) {
//            System.out.println("blog = " + blog);
//        }
//    }
//
//    @Test
//    void insertCategoryTest() {
//        System.out.println(categoryService.addCategory("mysql"));
//
//    }
//
//    @Test
//    void categoryIdsConvertTest() {
//        List<Category> categories = new ArrayList<>();
//        Category category = new Category();
//        Category category1 = new Category();
//        category1.setCategoryId(2L);
//
//        Category category2 = new Category();
//        category2.setCategoryId(3L);
//
//        Category category3 = new Category();
//        category3.setCategoryId(4L);
//
//        Category category4 = new Category();
//        category4.setCategoryId(5L);
//
//
//        categories.add(category);
//        categories.add(category1);
//        categories.add(category2);
//        categories.add(category3);
//        categories.add(category4);
//        String s = blogService.convertCategoryIds(categories);
//        System.out.println(s);
//    }




//
//    @Test
//    @DisplayName("查询blogTest")
//    void blogMapperFinalTest() {
//        List<Blog> blogListFinal = blogMapper.getBlogListFinal(2L, "1", 8);
//        for (Blog blog : blogListFinal) {
//            System.out.println(blog);
//        }
//    }
//

    @Test
    @DisplayName("多条件blogList查询")
    void blogMapperListTest() {
        List<Blog> blogList = adminBlogMapper.getBlogList(2L, "mysql",  0,8);
        System.out.println(blogList);
        for (Blog blog : blogList) {
            System.out.println("blog = " + blog);

        }
    }

    @Test
    @DisplayName("中文转拼音")
    void testString() {
        String str = "mysql索引底层数据结构解析";
        String s = PinyinHelper.toPinyin(str);
        String s1 = PinyinHelper.toPinyin(str, PinyinStyleEnum.NORMAL);
        System.out.println(s1.replace(' ', '-'));
    }

    @Test
    @DisplayName("treeMapTest")
    void testTreeMap() {
        HashMap<Object, Object> objectObjectTreeMap = new HashMap<>();
        objectObjectTreeMap.put(null,null);
        "test".equals("test");
    }


    @Test
    void testByte() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("/Users/logho/Documents/asiainfo/imageForTest/image-1.jpeg");
        byte[] bytes = fileInputStream.readAllBytes();
        System.out.println(bytes.length / 1024);
    }

}






