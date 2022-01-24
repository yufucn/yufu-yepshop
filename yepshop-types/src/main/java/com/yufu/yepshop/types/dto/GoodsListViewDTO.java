package com.yufu.yepshop.types.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author wang
 * @date 2022/1/23 22:55
 */
@Getter
@Setter
public class GoodsListViewDTO extends GoodsListDTO {
    private Date viewDate;
}
