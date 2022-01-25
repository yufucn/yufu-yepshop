package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.value.Location;
import com.yufu.yepshop.types.value.RegionValue;
import com.yufu.yepshop.types.value.SchoolValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import java.util.List;

/**
 * @author wang
 * @date 2021/12/26 22:56
 */
@Getter
@Setter
public class UserAccountDTO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "头像地址")
    private String avatarUrl;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "位置")
    private Location location;

    @ApiModelProperty(value = "用户手机号")
    private String mobile;

    @ApiModelProperty(value = "微信openId")
    private String openId;

    @ApiModelProperty(value = "微信unionId")
    private String unionId;

    @ApiModelProperty(value = "所在地")
    private RegionValue region;

    @ApiModelProperty(value = "我是否已关注")
    private Boolean followed;
}
