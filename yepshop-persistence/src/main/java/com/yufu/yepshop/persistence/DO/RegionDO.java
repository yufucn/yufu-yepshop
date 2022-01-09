package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author wang
 * @date 2022/1/8 12:54
 */
@Getter
@Setter
@Entity(name = "yufu_region")
public class RegionDO extends FullAuditedEntity {

    private Long parentId;

    private String name;

    private Integer type;

    private Integer code;
}
