package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.enums.AuditState;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.value.GoodsValue;
import com.yufu.yepshop.types.value.Seller;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/9 22:16
 */
@Getter
@Setter
public class GoodsListDTO {

    private GoodsValue goods;

    private String categoryId;

    private Integer originalPrice;

    private Seller seller;

    private GoodsState goodsState;

    private AuditState auditState;
}
