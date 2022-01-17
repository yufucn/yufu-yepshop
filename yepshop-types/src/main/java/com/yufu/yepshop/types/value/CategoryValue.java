package com.yufu.yepshop.types.value;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

/**
 * @author wang
 * @date 2021/12/26 21:53
 */
@Getter
@Setter
@Embeddable
public class CategoryValue extends ValueObject{
    /**
     * 分类Code
     */
    private String id;
    /**
     * 分类明细Code
     */
    private String name;
}
