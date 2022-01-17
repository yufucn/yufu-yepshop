package com.yufu.yepshop.mdm.api;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.shared.BaseController;
import com.yufu.yepshop.types.dto.UploadConfigDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wang
 * @date 2022/1/8 17:26
 */
@Api(tags = "主数据 - 配置")
@RestController
@RequestMapping("/api/v1/mdm/config")
public class ConfigController extends BaseController {

    @Value("${aliyun.ak}")
    private String ak;

    @Value("${aliyun.sk}")
    private String sk;

    @Value("${aliyun.region}")
    private String region;

    @Value("${aliyun.arn}")
    private String arn;

    @Value("${aliyun.bucket}")
    private String bucket;

    @Value("${aliyun.host}")
    private String host;

    public ConfigController(){

    }

    @ApiOperation(value = "上传配置")
    @GetMapping("/upload")
    @SneakyThrows
    public Result<UploadConfigDTO> getOssUpload() {
        UploadConfigDTO configDTO = new UploadConfigDTO();
        String userId = currentUser().getId().toString();
        String prefix = configDTO.buildPrefix(userId);
        configDTO.setRegion(region);
        configDTO.setPrefix(prefix);
        configDTO.setBucket(bucket);
        configDTO.setHost(host);
        DefaultProfile profile = DefaultProfile.getProfile(
                region,
                ak,
                sk);
        IAcsClient client = new DefaultAcsClient(profile);
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setRoleArn(arn);
        request.setRoleSessionName(userId);
        try {
            AssumeRoleResponse response = client.getAcsResponse(request);
            AssumeRoleResponse.Credentials credentials = response.getCredentials();
            AssumeRoleResponse.AssumedRoleUser assumedRoleUser = response.getAssumedRoleUser();
            configDTO.setAccessKeyId(credentials.getAccessKeyId());
            configDTO.setAccessKeySecret(credentials.getAccessKeySecret());
            configDTO.setSecurityToken(credentials.getSecurityToken());
            configDTO.setExpiration(credentials.getExpiration());
            configDTO.setArn(assumedRoleUser.getArn());
            configDTO.setAssumedRoleId(assumedRoleUser.getAssumedRoleId());
            return Result.success(configDTO);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        return null;
    }
}
