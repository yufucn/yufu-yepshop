package com.yufu.yepshop.types.value;

/**
 * @author wang
 * @date 2021/12/26 18:49
 */

public class Address extends Region {
    /**
     * 姓名
     */
    private String name;

    /**
     * 街道地址
     */
    private String street;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
