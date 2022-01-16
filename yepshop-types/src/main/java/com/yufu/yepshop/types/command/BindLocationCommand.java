package com.yufu.yepshop.types.command;

import com.yufu.yepshop.types.value.Location;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/16 22:44
 */
@Getter
@Setter
public class BindLocationCommand {

    private Location location;
}
