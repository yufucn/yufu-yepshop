package com.yufu.yepshop.types.command;

import com.yufu.yepshop.types.value.SchoolValue;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/20 0:23
 */
@Getter
@Setter
public class BindSchoolCommand {
    private List<SchoolValue> schools;
}
