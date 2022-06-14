package com.yufu.yepshop.types.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wang
 * @date 2022/2/16 23:37
 */
@Setter
@Getter
@AllArgsConstructor
public class RequirementQuery {
    @ApiModelProperty(value = "学校id集合")
    private List<String> schoolIds;
    @ApiModelProperty(value = "品类id集合")
    private List<String> categoryIds;
    @ApiModelProperty(value = "成色id集合")
    private List<String> conditionIds;
    private Integer page = 0;
    private Integer perPage = 10;
    private String keyword;
    private String auditState;
    private String requirementState;
}
