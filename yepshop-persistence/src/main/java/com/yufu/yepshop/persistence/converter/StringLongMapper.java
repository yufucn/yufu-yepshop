package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.ConvertUtils;
import org.springframework.stereotype.Component;

/**
 * @author wang
 * @date 2022/1/14 23:52
 */
@Component
public class StringLongMapper {
    public Long stringToLong(String id) {
        return ConvertUtils.getLongId(id);
    }

    public String longToString(Long id) {
        return ConvertUtils.getStringId(id);
    }
}
