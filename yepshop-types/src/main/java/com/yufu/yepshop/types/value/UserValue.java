package com.yufu.yepshop.types.value;

import com.yufu.yepshop.types.enums.SellerType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/29 15:37
 */
@Getter
@Setter
public class UserValue extends ValueObject{

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;
}
