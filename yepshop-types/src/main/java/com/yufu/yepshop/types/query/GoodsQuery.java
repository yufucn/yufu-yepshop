package com.yufu.yepshop.types.query;

import com.yufu.yepshop.types.enums.SortFilter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/16 21:12
 */
@Setter
@Getter
//@AllArgsConstructor
public class GoodsQuery {
    @ApiModelProperty(value = "学校id集合")
    private List<String> schoolIds;
    @ApiModelProperty(value = "品类id集合")
    private List<String> categoryIds;
    @ApiModelProperty(value = "成色id集合")
    private List<String> conditionIds;
    private Integer page = 0;
    private Integer perPage = 10;
    private String keyword;
    @ApiModelProperty(value = "排序字段：ALL、PRICE_ASC、PRICE_DESC、LATEST")
    private String sort;
}
