package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.CreationAuditedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author wang
 * @date 2021/12/27 23:51
 */
@Getter
@Setter
@Entity(name = "yufu_user_fans")
public class UserFansDO extends CreationAuditedEntity {
    private Long userId;
    private Long fansId;
}
