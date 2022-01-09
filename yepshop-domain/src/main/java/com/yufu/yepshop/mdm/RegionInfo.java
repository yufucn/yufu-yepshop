package com.yufu.yepshop.mdm;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/8 14:09
 */
@Getter
@Setter
public class RegionInfo {
    private Long id;

    private Long parentId;

    private String name;

    private Integer type;

    private Integer code;
}
