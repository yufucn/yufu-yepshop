package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.enums.OrderRateResult;
import com.yufu.yepshop.types.enums.OrderRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

/**
 * @author wang
 * @date 2022/2/16 22:42
 */
@Getter
@Setter
public class OrderRateDTO {

    private String orderId;

    private OrderRole role;

    private OrderRateResult result;

    private String content;

    /**
     * 是否匿名,卖家评不能匿名。
     * 可选值:true(匿名),false(非匿名)。注意：如果交易订单匿名，则评价也匿名
     */
    private Boolean anonymous;

    private Date creationTime;
}
