package com.logho.service.admin;

import com.logho.pojo.Category;
import com.logho.vo.BlogCategoryNum;
import com.logho.vo.BlogVO;

import java.util.List;

public interface CategoryService {

    List<Category> getCategoryList();

    Category getCategoryById(Long id);

    List<Category> getCategoryByBLogId(Long id);

    boolean addCategory(String categoryName);

    boolean deleteCategory(Long categoryId);

    boolean updateCategory(Long categoryId, String categoryName);

    List<Category> getAllCategories(String categories);

    List<BlogCategoryNum> getEachBlogNum();

    List<BlogVO> getBlogByCategory(Long categoryId,int startPage);
}