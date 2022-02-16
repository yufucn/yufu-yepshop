package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import com.yufu.yepshop.types.enums.AuditState;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.enums.SellerType;
import com.yufu.yepshop.types.value.GoodsValue;
import com.yufu.yepshop.types.value.RegionValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author wang
 * @date 2021/12/28 0:07
 */
@Getter
@Setter
@Entity(name = "yufu_goods")
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
public class GoodsDO extends FullAuditedEntity {

    private Long schoolId;

    private Long sellerId;

    private String title;

    private String picUrl;

    private String skuId;
    /**
     * 价格，单位为分
     */
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer price;

    private Long categoryId;

    private Long conditionId;

    /**
     * 价格，单位为分
     */
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer originalPrice;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer postFee;

//    @Column(nullable = false, columnDefinition = "int default 0")
//    private Integer totalComment;

    @Column(nullable = false, columnDefinition = "int default 0")
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

    @Embedded
    private RegionValue region;
}
