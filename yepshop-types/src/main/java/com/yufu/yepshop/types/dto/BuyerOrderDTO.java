package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.enums.OrderState;
import com.yufu.yepshop.types.value.Seller;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/15 13:01
 */
@Getter
@Setter
public class BuyerOrderDTO {
    private String id;
    private Seller seller;
    private Integer totalFee;
    private Integer payment;
    private OrderState orderState;
    private List<OrderItemDTO> items;
}
