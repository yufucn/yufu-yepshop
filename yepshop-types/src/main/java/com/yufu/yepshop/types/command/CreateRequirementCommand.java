package com.yufu.yepshop.types.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/2/16 23:36
 */
@Getter
@Setter
public class CreateRequirementCommand {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "学校Id，弹窗让用户选择一个学校，提交接口后，缓存在小程序")
    private String schoolId;

    @ApiModelProperty(value = "分类Id")
    private String categoryId;

    @ApiModelProperty(value = "成色Id")
    private String conditionId;
}
