package com.logho.utils;

import com.github.houbb.pinyin.constant.enums.PinyinStyleEnum;
import com.github.houbb.pinyin.util.PinyinHelper;

public class PinYinTransUtils {
    public static String getTitlePinYin(String chinese) {
        String s = PinyinHelper.toPinyin(chinese, PinyinStyleEnum.NORMAL);
        return s.replace(' ','-');
    }
}
