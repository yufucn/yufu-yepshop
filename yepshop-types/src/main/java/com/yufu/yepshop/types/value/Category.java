package com.yufu.yepshop.types.value;

/**
 * @author wang
 * @date 2021/12/26 21:53
 */
public class Category {
    /**
     * 分类Code
     */
    private String categoryCode;
    /**
     * 分类明细Code
     */
    private String categoryItemCode;

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryItemCode() {
        return categoryItemCode;
    }

    public void setCategoryItemCode(String categoryItemCode) {
        this.categoryItemCode = categoryItemCode;
    }
}
