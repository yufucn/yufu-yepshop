package com.yufu.yepshop.external.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import com.wechat.pay.contrib.apache.httpclient.exception.ParseException;
import com.wechat.pay.contrib.apache.httpclient.exception.ValidationException;
import com.wechat.pay.contrib.apache.httpclient.notification.Notification;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationHandler;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationRequest;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import com.yufu.yepshop.config.WechatPayConfig;
import com.yufu.yepshop.external.dto.WechatPayData;
import com.yufu.yepshop.external.dto.WechatPayResponse;
import com.yufu.yepshop.types.dto.OrderDTO;
import com.yufu.yepshop.types.dto.OrderItemDTO;
import com.yufu.yepshop.types.dto.TradeDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wang
 * @date 2022/1/13 23:32
 */

@Service
public class ExternalWeChatPayServiceImpl {
    @Autowired
    private WechatPayConfig wechatConfig;


    private CloseableHttpClient httpClient;

    private PrivateKey merchantPrivateKey;
    private Verifier verifier;

    private void buildClient() {
        try {


            merchantPrivateKey = PemUtil.loadPrivateKey(
                    new FileInputStream(wechatConfig.getPrivateKeyPath()));
//        X509Certificate wechatPayCertificate = PemUtil.loadCertificate(
//                new ByteArrayInputStream(certificate.getBytes(StandardCharsets.UTF_8)));
//
//        ArrayList<X509Certificate> listCertificates = new ArrayList<>();
//        listCertificates.add(wechatPayCertificate);
//
//        httpClient = WechatPayHttpClientBuilder.create()
//                .withMerchant(wechatConfig.getMchId(), wechatConfig.getMchSerialNo(), merchantPrivateKey)
//                .withWechatPay(listCertificates)
//                .build();
            // 获取证书管理器实例
            String mchId = wechatConfig.getMchId();
            String mchSerialNo = wechatConfig.getMchSerialNo();
            String apiV3Key = wechatConfig.getApiV3Key();

            CertificatesManager certificatesManager = CertificatesManager.getInstance();
// 向证书管理器增加需要自动更新平台证书的商户信息
            certificatesManager.putMerchant(mchId, new WechatPay2Credentials(mchId,
                    new PrivateKeySigner(mchSerialNo, merchantPrivateKey)), apiV3Key.getBytes(StandardCharsets.UTF_8));
// ... 若有多个商户号，可继续调用putMerchant添加商户信息

// 从证书管理器中获取verifier
            verifier = certificatesManager.getVerifier(mchId);
            WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                    .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
                    .withValidator(new WechatPay2Validator(verifier));
// ... 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient

// 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签，并进行证书自动更新
            httpClient = builder.build();


        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HttpCodeException e) {
            e.printStackTrace();
        }
    }

    public String callBack(NotificationRequest request) throws ValidationException, ParseException {
        NotificationHandler handler = new NotificationHandler(verifier, wechatConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8));
        Notification notification = handler.parse(request);
        System.out.println(notification.toString());
        return "ok";
    }

    public Boolean verifiedSign(String serialNo, String signStr, String signature) throws UnsupportedEncodingException {
        return verifier.verify(serialNo, signStr.getBytes("utf-8"), signature);
    }

    public String decryptBody(String body) throws GeneralSecurityException {
        AesUtil aesUtil = new AesUtil(wechatConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8));

        JSONObject object = JSONUtil.parseObj(body);
        JSONObject resource = object.getJSONObject("resource");
        String ciphertext = resource.getStr("ciphertext");
        String associatedData = resource.getStr("associated_data");
        String nonce = resource.getStr("nonce");

        return aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8),
                nonce.getBytes(StandardCharsets.UTF_8), ciphertext);
    }

    public Map<String, String> convertWechatPayMsgToMap(String plainBody) {

        Map<String, String> paramsMap = new HashMap<>(2);

        JSONObject jsonObject = JSONUtil.parseObj(plainBody);

        paramsMap.put("appid", jsonObject.getStr("appid"));
        paramsMap.put("mchid", jsonObject.getStr("mchid"));
        paramsMap.put("out_trade_no", jsonObject.getStr("out_trade_no"));
        paramsMap.put("transaction_id", jsonObject.getStr("transaction_id"));
        paramsMap.put("trade_type", jsonObject.getStr("trade_type"));
        paramsMap.put("trade_state", jsonObject.getStr("trade_state"));
        paramsMap.put("trade_state_desc", jsonObject.getStr("trade_state_desc"));
        paramsMap.put("bank_type", jsonObject.getStr("bank_type"));
        paramsMap.put("success_time", jsonObject.getStr("success_time"));

        return paramsMap;

    }


    private String cut(String source, int length) {
        if (source != null) {
            if (source.length() <= length) {
                return source;
            }
            return source.substring(0, length - 1);
        }
        return "";
    }

    public String transfer(){
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/transfer/batches");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        String tid = "973349170452762624";
        rootNode
                .put("appid", wechatConfig.getAppid())
                .put("out_batch_no", tid)
                .put("batch_name", "测试打款")
                .put("batch_remark", "测试打款")
                .put("total_amount", 1)
                .put("total_num", 1)
        ;
        ArrayNode jsonConditions = rootNode.putArray("transfer_detail_list");
        ObjectNode jsonCondition = new ObjectMapper().createObjectNode();
        jsonCondition.put("out_detail_no", tid);
        jsonCondition.put("transfer_amount", 1);
        jsonCondition.put("transfer_remark", "测试打款");
        jsonCondition.put("openid", "o3aJ55ozZhjwPc0ExOy4P0uy9hMw");
        jsonConditions.add(jsonCondition);
        try {
            objectMapper.writeValue(bos, rootNode);
            httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
            this.buildClient();
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String s = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSONUtil.parseObj(s);
            return s;
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    public WechatPayResponse pay(List<TradeDTO> trades) {
        if (trades.size() == 1) {
            TradeDTO trade = trades.get(0);
            OrderDTO order = trade.getOrder();
            OrderItemDTO item = order.getItems().get(0);
            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi");
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode rootNode = objectMapper.createObjectNode();

            rootNode.put("mchid", wechatConfig.getMchId())
                    .put("appid", wechatConfig.getAppid())
                    .put("description", cut(item.getGoods().getTitle(), 127))
                    .put("notify_url", wechatConfig.getNotifyUrl())
                    .put("out_trade_no", trade.getId())
                    .put("attach", item.getGoods().getId())
            ;
//            rootNode.putObject("amount")
//                    .put("total", order.getPayment());
            // todo:调试阶段金额为1分钱
            rootNode.putObject("amount")
                    .put("total", 1);
            rootNode.putObject("payer")
                    .put("openid", trade.getOpenId());
            try {
                objectMapper.writeValue(bos, rootNode);
                httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
                this.buildClient();
                CloseableHttpResponse response = httpClient.execute(httpPost);
                String s = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = JSONUtil.parseObj(s);
                String prepayId = jsonObject.getStr("prepay_id");
                WechatPayResponse res = new WechatPayResponse();
                if (prepayId != null) {
                    WechatPayData data = new WechatPayData();
                    data.setAppId(wechatConfig.getAppid());
                    data.setTimeStamp(PayUtil.getCurrentTimeStamp());
                    data.setNonceStr(PayUtil.makeUuid(32));
                    data.setPackageInfo("prepay_id=" + prepayId);
                    data.setSignType("RSA");
                    String paySignStr = data.paySignStr();
                    String signature = PayUtil.sign(paySignStr.getBytes(StandardCharsets.UTF_8), merchantPrivateKey);
                    data.setPaySign(signature);
                    res.setPay(data);
                } else {
                    String code = jsonObject.getStr("code");
                    String message = jsonObject.getStr("message");
                    res.setCode(code);
                    res.setMessage(message);
                }
                return res;
            } catch (Exception e) {
                WechatPayResponse res = new WechatPayResponse();
                res.setMessage(e.getMessage());
            }
        } else {
//            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/combine-transactions/jsapi");
//            httpPost.addHeader("Accept", "application/json");
//            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            TradeDTO trade = trades.get(0);
//            OrderDTO order = trade.getOrder();
//            OrderItemDTO item = order.getItems().get(0);
//            String tradeNo = order.getId();
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            ObjectNode rootNode = objectMapper.createObjectNode();
//            rootNode.put("combine_mchid", mchId)
//                    .put("combine_appid", appid)
//
//                    .put("notify_url", notifyUrl)
//                    .put("combine_out_trade_no", tradeNo);
//            ArrayNode subOrders = rootNode.putArray("sub_orders");
//            for (TradeDTO o : trades) {
//                ObjectNode att = objectMapper.createObjectNode();
//                att.put("mchid", mchId);
//                att.putObject("amount").put("total_amount", o.getOrder().getPayment());
//                att.put("description", item.getGoods().getTitle());
//                subOrders.add(att);
//            }
//            rootNode.putObject("combine_payer_info")
//                    .put("openid", trade.getOpenId());
//            try {
//                objectMapper.writeValue(bos, rootNode);
//                httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
////                CloseableHttpResponse response = httpClient.execute(httpPost);
////                String bodyAsString = EntityUtils.toString(response.getEntity());
//            } catch (Exception e) {
//
//            }
        }
        return null;
    }
}
