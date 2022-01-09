package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.CreationAuditedEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * @author wang
 * @date 2021/12/27 23:49
 */
@Getter
@Setter
@Entity(name = "yufu_user_collect")
@EntityListeners(AuditingEntityListener.class)
public class UserCollectDO extends CreationAuditedEntity {
    private Long goodsId;
}
