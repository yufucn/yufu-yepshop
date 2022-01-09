package com.yufu.yepshop.types.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author wang
 * @date 2021/12/26 21:17
 */
@Getter
@Setter
@Embeddable
@MappedSuperclass
public class RegionValue implements Serializable {
    /**
     * 国家
     */
    @Column(length = 128)
    private String country;

    /**
     * 省份
     */
    @Column(length = 128)
    private String province;

    /**
     * 城市
     */
    @Column(length = 128)
    private String city;

    /**
     * 地区
     */
    @Column(length = 128)
    private String area;
}
