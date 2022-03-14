package com.yufu.yepshop.types.command;

import com.yufu.yepshop.types.enums.AuditState;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/2/28 23:18
 */
@Getter
@Setter
public class AuditCommand {
    private AuditState auditState;
}
