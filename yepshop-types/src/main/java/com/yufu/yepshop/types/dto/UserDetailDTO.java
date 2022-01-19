package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.value.SchoolValue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/19 22:48
 */
@Getter
@Setter
public class UserDetailDTO {
    private UserAccountDTO account;
    private List<SchoolValue> schools;
    private UserDTO user;
}
