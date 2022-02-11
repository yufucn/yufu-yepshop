package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import com.yufu.yepshop.types.enums.Platform;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author wang
 * @date 2022/2/11 21:46
 */
@Getter
@Setter
@Entity(name = "yufu_slider")
@EntityListeners(AuditingEntityListener.class)
public class SliderDO extends FullAuditedEntity {

    private String imageUrl;

    private String url;

    @Column(length = 32)
    private String target;

    @Column(length = 64)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private Platform platform;

    @Column(length = 128)
    private String pageId;

    @Column(length = 128)
    private String positionId;

    private String description;


    private Integer sortId;
}
