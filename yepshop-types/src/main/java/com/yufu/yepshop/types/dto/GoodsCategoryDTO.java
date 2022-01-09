package com.yufu.yepshop.types.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/9 10:33
 */
@Getter
@Setter
public class GoodsCategoryDTO extends BaseDTO {
    private String parentId;

    private String name;

    private String description;

    private Integer sortId;
}
