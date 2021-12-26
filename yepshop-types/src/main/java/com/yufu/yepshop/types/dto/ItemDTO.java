package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.value.Category;
import com.yufu.yepshop.types.value.Money;

import java.util.List;

/**
 * @author wang
 * @date 2021/12/26 22:15
 */
public class ItemDTO {

    private String id;

    private String text;

    private List<String> urls;

    private Money price;

    private Money originalPrice;

    private Money postage;

    private List<Category> categories;

    /**
     * 评论总数
     */
    private Long totalComment;

    /**
     * 收藏总数
     */
    private Long totalFavorite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
