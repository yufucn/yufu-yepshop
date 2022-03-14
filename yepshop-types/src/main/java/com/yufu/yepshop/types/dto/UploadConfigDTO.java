package com.yufu.yepshop.types.dto;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wang
 * @date 2022/1/9 16:00
 */
@Getter
@Setter
public class UploadConfigDTO {
    private String bucket;
    private String region;
    private String accessKeyId;
    private String accessKeySecret;
    private String securityToken;
    private String prefix;
    private String avatarPrefix;
    private String expiration;
    private String arn;
    private String assumedRoleId;
    private String host;

    public String buildPrefix(String userId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        return String.format("goods/%s/%s/", userId, sdf.format(now));
    }

    public String buildAvatarPrefix(String userId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        return String.format("avatar/%s/%s/", userId, sdf.format(now));
    }
}
