package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.AuditedEntity;
import com.yufu.yepshop.domain.types.auditing.CreationAuditedEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * @author wang
 * @date 2022/1/19 23:09
 */
@Getter
@Setter
@Entity(name = "yufu_user_school")
@EntityListeners(AuditingEntityListener.class)
public class UserSchoolDO extends CreationAuditedEntity {

    private Long schoolId;

    @Column(length = 64)
    private String schoolName;

}
