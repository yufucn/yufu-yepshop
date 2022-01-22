package com.yufu.yepshop;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author wang
 * @date 2022/1/17 21:39
 */
public class ConvertUtils {

    public static Long getLongId(String id) {

        return id != null && !id.isEmpty() ? Long.parseLong(id) : null;
    }


    public static String getStringId(Long id) {
        if (id != null) {
            return id.toString();
        }
        return null;
    }
}
