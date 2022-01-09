package com.yufu.yepshop.types.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author wang
 * @date 2022/1/9 22:16
 */
@Getter
@Setter
public class GoodsListDTO {

    private String id;

    private String title;

    private String picUrl;

    private String categoryId;

    private BigDecimal price;

    private BigDecimal originalPrice;
}
