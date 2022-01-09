package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.CreationAuditedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author wang
 * @date 2021/12/27 23:49
 */
@Getter
@Setter
@Entity(name = "yufu_user_collect")
public class UserCollectDO extends CreationAuditedEntity {
    private Long goodsId;
}
