package com.yufu.yepshop.persistence.converter;

import org.springframework.stereotype.Component;

/**
 * @author wang
 * @date 2022/1/14 23:52
 */
@Component
public class StringLongMapper {
    public Long stringToLong(String string) {
        return string != null && !string.isEmpty() ? Long.parseLong(string) : null;
    }

    public String longToString(Long lon) {
        return lon.toString();
    }
}
