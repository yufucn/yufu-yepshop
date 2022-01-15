package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.enums.PayState;
import com.yufu.yepshop.types.enums.PayType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

/**
 * @author wang
 * @date 2022/1/14 22:05
 */
@Getter
@Setter
public class TradeDTO {
    /**
     * 交易订单号
     */
    private String id;

    /**
     * 合并交易订单号
     */
    private String combineId;

    private PayType payType;

    private PayState payState;

    private String openId;

    private String prepayId;

    /**
     * 实付金额
     */
    private Integer totalPayment;
    /**
     * 付款时间
     */
    private Date payTime;

    private OrderDTO order;
}
