package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author wang
 * @date 2022/1/8 12:32
 */
@Getter
@Setter
@Entity(name = "yufu_goods_category")
public class GoodsCategoryDO extends FullAuditedEntity {

    private Long parentId;

    private String name;

    @Column(length = 512)
    private String description;

    private Integer sortId;
}
