package com.yufu.yepshop.types.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yufu.yepshop.types.enums.SellerType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/15 15:15
 */
@Getter
@Setter
public class Seller extends ValueObject{

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "卖家类型")
    private SellerType type;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;
}
