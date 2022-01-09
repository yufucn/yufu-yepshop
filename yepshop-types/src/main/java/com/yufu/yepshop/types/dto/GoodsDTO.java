package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.value.RegionValue;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wang
 * @date 2022/1/9 18:33
 */
@Getter
@Setter
public class GoodsDTO {

    private String id;

    private String title;

    private String picUrl;

    private String categoryId;

    private BigDecimal price;

    private BigDecimal originalPrice;

    private RegionValue region;

    private String[] imageUrls;

    private String text;

    private Integer totalComment;

    private Integer totalCollect;
}
