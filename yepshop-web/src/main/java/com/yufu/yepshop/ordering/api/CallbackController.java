package com.yufu.yepshop.ordering.api;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wechat.pay.contrib.apache.httpclient.exception.ParseException;
import com.wechat.pay.contrib.apache.httpclient.exception.ValidationException;
import com.wechat.pay.contrib.apache.httpclient.notification.Notification;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationHandler;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationRequest;
import com.yufu.yepshop.application.TradeService;
import com.yufu.yepshop.external.impl.ExternalWeChatPayServiceImpl;
import com.yufu.yepshop.types.event.PaymentReceivedEvent;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wang
 * @date 2022/5/9 23:03
 */
@Api(tags = "Shop - 订单 - 回调")
@RestController
@RequestMapping("/callback")
public class CallbackController {

    @Autowired
    private ExternalWeChatPayServiceImpl externalWeChatPayService;

    @Autowired
    private TradeService tradeService;

    @PostMapping("/wechat")
    public Map<String, String> wechat(HttpServletRequest request, HttpServletResponse response) throws ValidationException, ParseException {
        //获取报文
        String body = getRequestBody(request);
        //随机串
        String nonceStr = request.getHeader("Wechatpay-Nonce");
        //微信传递过来的签名
        String signature = request.getHeader("Wechatpay-Signature");
        //证书序列号（微信平台）
        String serialNo = request.getHeader("Wechatpay-Serial");
        //时间戳
        String timestamp = request.getHeader("Wechatpay-Timestamp");
        //构造签名串

        //应答时间戳\n
        //应答随机串\n
        //应答报文主体\n
        String signStr = Stream.of(timestamp, nonceStr, body).collect(Collectors.joining("\n", "", "\n"));

        Map<String, String> map = new HashMap<>(2);
        try {
            //验证签名是否通过
            boolean result = externalWeChatPayService.verifiedSign(serialNo, signStr, signature);

            if (result) {
                //解密数据
                String plainBody = externalWeChatPayService.decryptBody(body);
                System.out.println(plainBody);
                JSONObject jsonObject = JSONUtil.parseObj(plainBody);
                String outTradeNo = jsonObject.getStr("out_trade_no");
                String trade_type = jsonObject.getStr("trade_type");
                String trade_state = jsonObject.getStr("trade_state");
                String success_time = jsonObject.getStr("success_time");
                String transaction_id = jsonObject.getStr("transaction_id");
                if ("SUCCESS".equals(trade_state)) {
                    tradeService.paySuccess(new PaymentReceivedEvent(outTradeNo, transaction_id, success_time));
                }
//                log.info("解密后的明文:{}",plainBody);

//                Map<String, String> paramsMap = externalWeChatPayService.convertWechatPayMsgToMap(plainBody);
                //处理业务逻辑 TODO

                //响应微信
                map.put("code", "SUCCESS");
                map.put("message", "成功");
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
//            log.error("微信支付回调异常:{}", e);
        }

        return map;

    }


    private String getRequestBody(HttpServletRequest request) {

        StringBuffer sb = new StringBuffer();

        try (ServletInputStream inputStream = request.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
//            log.error("读取数据流异常:{}", e);
        }

        return sb.toString();

    }
}
