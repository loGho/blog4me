package com.logho.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.logho.constants.UserContants;
import com.logho.pojo.Category;
import com.logho.service.admin.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = {"/category/list/{pagenum}","/category/list"})
    public String categoryList(Model model, @PathVariable(value = "pagenum",required = false) Integer pageNum) {
        PageHelper.startPage(pageNum==null?1:pageNum,8);
        List<Category> categoryList = categoryService.getCategoryList();
        PageInfo<Category>  categoryPageInfo = new PageInfo<>(categoryList);
        model.addAttribute("categoryList",categoryPageInfo);
        return "admin/category_manage";
    }

    @GetMapping(value = {"/category/add/{id}","/category/add"})
    public String toAddCategoryPost(@PathVariable(value = "id",required = false) Long id,
                                Model model) {
        if (id != null) {
            Category category = categoryService.getCategoryById(id);
            model.addAttribute("category",category);
            return "admin/category_post";
        }
        model.addAttribute("category",null);
        return "admin/category_post";
    }


    @PostMapping(value = {"/category/save/{id}","/category/save"})
    public String saveCategory(@Validated Category categoryName, RedirectAttributes model,
                               @PathVariable(value = "id",required = false) Long categoryId) {
        if (categoryId!=null){
            //执行更新分类的方法
            boolean flag = categoryService.updateCategory(categoryId, categoryName.getCategoryName());
            if (!flag){
                System.out.println("=======");
                model.addFlashAttribute(UserContants.OPERATION_STATUS,"操作失败，所要修改的标签已经存在！");
                return "redirect:/admin/category/list";
            }
            model.addFlashAttribute(UserContants.OPERATION_STATUS,"操作成功,该标签已被成功修改！");
            return "redirect:/admin/category/list";
        }
        boolean flag = categoryService.addCategory(categoryName.getCategoryName());
        if (!flag) {
            model.addFlashAttribute(UserContants.OPERATION_STATUS,"操作失败,该标签已经存在！");
            return "redirect:/admin/category/list";
        }
        model.addFlashAttribute(UserContants.OPERATION_STATUS,"操作成功,该标签已被成功添加！");
        return "redirect:/admin/category/list";
    }

    @GetMapping("/category/del/{id}")
    public String delCategory(@PathVariable("id") Long categoryId,
                              RedirectAttributes model) {
        boolean flag = categoryService.deleteCategory(categoryId);
        if (!flag){
//            System.out.println("=======");
            model.addFlashAttribute(UserContants.OPERATION_STATUS,"操作失败，所删除的标签不存在！");
            return "redirect:/admin/category/list";
        }
        model.addFlashAttribute(UserContants.OPERATION_STATUS,"操作成功,该标签已被成功删除！");
        return "redirect:/admin/category/list";
    }
}
