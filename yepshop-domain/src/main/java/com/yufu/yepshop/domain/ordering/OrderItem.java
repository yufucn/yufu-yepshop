package com.yufu.yepshop.domain.ordering;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/13 21:09
 */
@Getter
@Setter
public class OrderItem {

    private String goodsId;

    private String skuId;

    private String goodsTitle;

    private String goodsPicUrl;

    private Integer price;

    private Integer num;
}
