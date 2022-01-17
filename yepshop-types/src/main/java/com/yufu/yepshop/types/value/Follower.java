package com.yufu.yepshop.types.value;

import com.yufu.yepshop.types.enums.FollowState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/18 0:06
 */
@Getter
@Setter
public class Follower extends ValueObject {
    @ApiModelProperty(value = "用户Id")
    private String id;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户头像")
    private String avatarUrl;

    @ApiModelProperty(value = "学校Id")
    private String schoolId;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "粉丝数")
    private Integer followers;

    @ApiModelProperty(value = "我的状态")
    private FollowState myFollowState;
}
