package com.yufu.yepshop.types.command;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/9 10:42
 */
@Getter
@Setter
public class CreateGoodsCategoryCommand {
    private String parentId;

    private String name;

    private String description;

    private Integer sortId;
}
