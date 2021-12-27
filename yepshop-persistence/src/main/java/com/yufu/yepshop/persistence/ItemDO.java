package com.yufu.yepshop.persistence;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wang
 * @date 2021/12/28 0:07
 */
public class ItemDO {
    private Long id;

    private String title;

    private String imageUrl;

    private BigDecimal price;

    private BigDecimal originalPrice;

    private BigDecimal postage;

    private Integer totalComment;

    private Integer totalFavorite;

    private Date creationTime;

    private Long creatorId;

    private Integer status;
}
