package com.yufu.yepshop.persistence;

import java.math.BigDecimal;

/**
 * @author wang
 * @date 2021/12/28 0:42
 */
public class OrderItem {
    private Long id;

    private Long orderId;

    private Long itemId;

    private String itemTitle;

    private String itemImageUrl;

    private BigDecimal unitPrice;

    private BigDecimal discount;

    private Integer units;


}
