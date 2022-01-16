package com.yufu.yepshop.types.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/15 15:20
 */
@Getter
@Setter
public class Buyer {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @JsonIgnore
    public Long getLongId(){
        return Long.parseLong(id);
    }
}
