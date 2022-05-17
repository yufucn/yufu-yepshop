package com.yufu.yepshop.config;

import com.yufu.yepshop.types.enums.AuditState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wang
 * @date 2022/5/17 23:56
 */
@Component
@ConfigurationProperties(prefix = "yepxiao")
@Getter
@Setter
public class YepxiaoConfig {
    private String filterAuditState;

    public List<AuditState> status() {
        List<AuditState> result = new ArrayList<>();
        for (String s : filterAuditState.split(",")) {
            result.add(AuditState.valueOf(s));
        }
        return result;
    }
}
