package com.yufu.yepshop.types.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

/**
 * @author wang
 * @date 2022/1/17 0:19
 */
@Setter
@Getter
@Embeddable
@MappedSuperclass
public class SchoolValue extends ValueObject{
    @ApiModelProperty(value = "学校Id")
    private String id;

    @ApiModelProperty(value = "学校名称")
    private String name;
}
