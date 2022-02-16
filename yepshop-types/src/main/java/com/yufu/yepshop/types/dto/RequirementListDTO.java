package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.enums.AuditState;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.enums.RequirementState;
import com.yufu.yepshop.types.value.SchoolValue;
import com.yufu.yepshop.types.value.UserValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author wang
 * @date 2022/2/16 0:17
 */
@Getter
@Setter
public class RequirementListDTO {

    private String id;

    private String title;

    private SchoolValue school;

    @ApiModelProperty(value = "分类Id")
    private String categoryId;

    @ApiModelProperty(value = "成色Id")
    private String conditionId;

    private RequirementState requirementState;

    private AuditState auditState;

    private Date creationTime;

    private UserValue user;
}
