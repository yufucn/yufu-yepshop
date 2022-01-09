package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.common.Constants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author wang
 * @date 2021/12/28 0:15
 */
@Getter
@Setter
@Entity(name = "yufu_goods_detail")
public class GoodsDetailDO {
    @Id
    private Long id;

    @Column(length = 3000)
    private String imageUrls;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String text;
}
