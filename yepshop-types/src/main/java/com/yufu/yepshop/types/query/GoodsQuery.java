package com.yufu.yepshop.types.query;

import com.yufu.yepshop.types.enums.SortFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/16 21:12
 */
@Setter
@Getter
@AllArgsConstructor
public class GoodsQuery {
    private String schoolId;
    private String categoryId;
    private String conditionId;
    private Integer page;
    private Integer perPage;
    private String keyword;
    private SortFilter sort;
}
