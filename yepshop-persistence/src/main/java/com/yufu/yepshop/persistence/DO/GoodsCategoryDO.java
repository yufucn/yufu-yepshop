package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * @author wang
 * @date 2022/1/8 12:32
 */
@Getter
@Setter
@Entity(name = "yufu_goods_category")
@EntityListeners(AuditingEntityListener.class)
public class GoodsCategoryDO extends FullAuditedEntity {

    private Long parentId;

    private String name;

    @Column(length = 512)
    private String description;

    private Integer sortId;
}
