package com.logho.dao.admin;

import com.logho.pojo.Category;
import com.logho.vo.BlogCategoryNum;
import com.logho.vo.BlogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("select * from category where category_name = #{categoryName}")
    Category getCategoryByName(String categoryName);

    @Select("select * from category where category_id = #{categoryId}")
    Category getCategoryById(Long categoryId);

    List<Category> getCategoryByBlogId(Long blogId);

    List<Category> getCategoryList();

    boolean addCategory(String categoryName);

    boolean deleteCategoryById(Long categoryId);

    boolean updateCategoryById(@Param("id") Long categoryId, @Param("categoryName") String categoryName);

    List<BlogCategoryNum> getBlogNumGroupByCategory();

    List<BlogVO> getBlogByCategory(@Param("categoryId") Long categoryId,@Param("startPage") Integer startPage);

    void deleteAllCategoryByBlogId(Long blogId);
}
