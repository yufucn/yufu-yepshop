package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.enums.AuditState;
import com.yufu.yepshop.types.enums.SellerType;
import com.yufu.yepshop.types.value.GoodsValue;
import com.yufu.yepshop.types.value.RegionValue;
import com.yufu.yepshop.types.value.Seller;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/9 18:33
 */
@Getter
@Setter
public class GoodsDTO {

    private GoodsValue goods;

    private String categoryId;

    private Integer originalPrice;

    private RegionValue region;

    private Seller seller;

    private AuditState auditState;

    private String[] imageUrlList;

    private String text;

    private Integer totalComment;

    private Integer totalCollect;


}
