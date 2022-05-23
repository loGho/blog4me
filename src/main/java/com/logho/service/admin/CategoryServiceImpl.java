package com.logho.service.admin;


import com.logho.dao.admin.CategoryMapper;
import com.logho.pojo.Category;
import com.logho.vo.BlogCategoryNum;
import com.logho.vo.BlogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategoryList() {

        List<Category> categoryList = categoryMapper.getCategoryList();
        return categoryList;
    }

    @Override
    public boolean addCategory(String categoryName) {
        Category category = categoryMapper.getCategoryByName(categoryName);
        if (category == null) {
            categoryMapper.addCategory(categoryName);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCategory(Long categoryId) {
        boolean flag = false;
        Category categoryById = categoryMapper.getCategoryById(categoryId);
        if (categoryById != null) {
            flag = categoryMapper.deleteCategoryById(categoryId);
        }
        return flag;
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryMapper.getCategoryById(id);
    }

    @Override
    public List<Category> getCategoryByBLogId(Long blogId) {
        return categoryMapper.getCategoryByBlogId(blogId);
    }

    @Override
    public boolean updateCategory(Long categoryId, String categoryName) {
        boolean flag = false;
        Category categoryById = categoryMapper.getCategoryById(categoryId);
        Category category = categoryMapper.getCategoryByName(categoryName);
        if (categoryById != null && category == null) {
            flag = categoryMapper.updateCategoryById(categoryId, categoryName);
        }
        return flag;
    }

    @Override
    public List<Category> getAllCategories(String categories) {
        List<Category> categoryList = new ArrayList<>();

        if (StringUtils.hasLength(categories)) {
            String[] categoryIds = categories.split(",");
            for (String categoryId : categoryIds) {
                categoryList.add(getCategoryById(Long.valueOf(categoryId)));
            }
        }
        return categoryList;
    }

    @Override
    public List<BlogCategoryNum> getEachBlogNum() {
       return categoryMapper.getBlogNumGroupByCategory();
    }

    @Override
    public List<BlogVO> getBlogByCategory(Long categoryId,int startPage) {
        return categoryMapper.getBlogByCategory(categoryId, startPage);
    }
}

