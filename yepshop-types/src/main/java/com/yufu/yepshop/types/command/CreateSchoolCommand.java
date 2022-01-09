package com.yufu.yepshop.types.command;

import com.yufu.yepshop.types.value.AddressValue;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/8 12:45
 */

@Getter
@Setter
public class CreateSchoolCommand {

    private String name;

    private String abbreviation;

    private AddressValue address;

    private Integer sortId;
}
