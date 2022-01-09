package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * @author wang
 * @date 2022/1/8 12:54
 */
@Getter
@Setter
@Entity(name = "yufu_region")
@EntityListeners(AuditingEntityListener.class)
public class RegionDO extends FullAuditedEntity {

    private Long parentId;

    private String name;

    private Integer type;

    private Integer code;
}
