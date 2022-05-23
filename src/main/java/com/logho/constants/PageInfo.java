package com.logho.constants;

import lombok.Data;

@Data
public class PageInfo {
    public static final int PAGE_SIZE = 8;
    private Integer recordTotal;
    private Integer pageNumTotal;
    private Integer curNum;
}
