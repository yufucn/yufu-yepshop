package com.yufu.yepshop.domain.goods;

import com.yufu.yepshop.types.value.RegionValue;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author wang
 * @date 2021/12/26 22:25
 */
@Getter
@Setter
public class Goods {
    private String id;

    private String title;

    private String picUrl;

    private String categoryId;

    private BigDecimal price;

    private BigDecimal originalPrice;

    private RegionValue region;

    private String imageUrls;

    private String text;

    public String getTitleFromText() {
        int length = 64;
        if (text.length() > length) {
            return text.substring(0, length - 1);
        }
        return text;
    }
}
