package com.yufu.yepshop.domain.goods;

import com.yufu.yepshop.types.enums.AuditState;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.enums.SellerType;
import com.yufu.yepshop.types.value.RegionValue;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2021/12/26 22:25
 */
@Getter
@Setter
public class Goods {
    private Long id;

    private String title;

    private String picUrl;

    private String categoryId;

    private Integer price;

    private Integer originalPrice;

    private RegionValue region;

    private String imageUrls;

    private String text;

    private Integer totalComment;

    private Integer totalCollect;

    private SellerType sellerType;

    private GoodsState goodsState;

    private AuditState auditState;

    private Long creatorId;

    public String getTitleFromText() {
        int length = 64;
        if (text.length() > length) {
            return text.substring(0, length - 1);
        }
        return text;
    }

    public Boolean statusCheck() {
        if (goodsState == GoodsState.UP && auditState == AuditState.SUCCESS) {
            return true;
        }
        return false;
    }
}
