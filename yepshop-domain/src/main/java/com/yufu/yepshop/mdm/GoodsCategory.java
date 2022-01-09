package com.yufu.yepshop.mdm;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

/**
 * @author wang
 * @date 2022/1/9 10:30
 */
@Getter
@Setter
public class GoodsCategory {
    private String id;

    private String parentId;

    private String name;

    private String description;

    private Integer sortId;
}
