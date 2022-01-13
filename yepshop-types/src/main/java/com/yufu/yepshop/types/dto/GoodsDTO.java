package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.value.RegionValue;
import lombok.Getter;
import lombok.Setter;

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

    private Integer price;

    private Integer originalPrice;

    private RegionValue region;

    private String[] imageUrls;

    private String text;

    private Integer totalComment;

    private Integer totalCollect;
}
