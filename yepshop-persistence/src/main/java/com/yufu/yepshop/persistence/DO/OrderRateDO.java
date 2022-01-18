package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.CreationAuditedEntity;
import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import com.yufu.yepshop.types.enums.OrderRateResult;
import com.yufu.yepshop.types.enums.OrderRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author wang
 * @date 2022/1/18 21:52
 */
@Getter
@Setter
@Entity(name = "yufu_order_rate")
@EntityListeners(AuditingEntityListener.class)
public class OrderRateDO  extends FullAuditedEntity {

    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private OrderRole role;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private OrderRateResult result;

    @Column(length = 500)
    private String content;

    /**
     * 是否匿名,卖家评不能匿名。
     * 可选值:true(匿名),false(非匿名)。注意：如果交易订单匿名，则评价也匿名
     */
    private Boolean anonymous;
}
