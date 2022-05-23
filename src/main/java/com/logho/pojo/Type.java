package com.logho.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Type {

    private Long typeId;
    private String typeName;

    private List<Blog> blogList;
}
