package com.yufu.yepshop.types.command;


import com.yufu.yepshop.types.value.Category;
import com.yufu.yepshop.types.value.Money;
import com.yufu.yepshop.types.value.Region;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wang
 * @date 2021/12/26 20:39
 */

public class CreateItemCommand {

    @NotNull(message = "内容不能为空")
    private String text;

    @NotNull(message = "图片不能为空")
    private List<String> urls;

    @NotNull(message = "金额不能为空")
    private Money price;

    private Money originalPrice;

    private Money postage;

    private List<Category> categories;

    @NotNull(message = "发货地址不能为空")
    private Region shippingAddress;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public Region getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Region shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Money getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Money originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Money getPostage() {
        return postage;
    }

    public void setPostage(Money postage) {
        this.postage = postage;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
