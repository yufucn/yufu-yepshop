package com.yufu.yepshop.persistence;

import com.yufu.yepshop.types.value.Address;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wang
 * @date 2021/12/28 0:30
 */
public class OrderDO {

    private Long id;

    private Address address;

    private Integer status;

    private BigDecimal totalPrice;

    private Long buyId;

    private Date creationTime;

    private Long creatorId;
}
