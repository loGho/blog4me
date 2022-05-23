package com.logho.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class Category {

    private Long categoryId;

    @NotBlank(message = "标签名不能为空！")
    private String categoryName;

    private List<Blog> blogList;
}
