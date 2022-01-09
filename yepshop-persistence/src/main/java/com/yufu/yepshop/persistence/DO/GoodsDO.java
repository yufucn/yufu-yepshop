package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import com.yufu.yepshop.types.enums.AuditState;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.enums.SellerType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author wang
 * @date 2021/12/28 0:07
 */
@Getter
@Setter
@Entity(name = "yufu_goods")
@EntityListeners(AuditingEntityListener.class)
public class GoodsDO extends FullAuditedEntity {

    private Long schoolId;

    private Long sellerId;

    private String title;

    private String picUrl;

    private Long categoryId;

    private BigDecimal price;

    private BigDecimal originalPrice;

    private Integer totalComment;

    private Integer totalCollect;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private SellerType sellerType;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private GoodsState goodsState;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private AuditState auditState;
}
