package com.yufu.yepshop.external.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import com.yufu.yepshop.domain.ordering.Order;
import com.yufu.yepshop.domain.ordering.OrderItem;
import com.yufu.yepshop.external.ExternalWeChatPayService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author wang
 * @date 2022/1/13 23:32
 */
@Service
public class ExternalWeChatPayServiceImpl implements ExternalWeChatPayService {

    private final CloseableHttpClient httpClient;

    @Value("wechat.pay.privateKey")
    private String privateKey;

    @Value("wechat.pay.mchId")
    private String mchId;

    @Value("wechat.pay.mchSerialNo")
    private String mchSerialNo;

    @Value("wechat.pay.certificate")
    private String certificate;

    @Value("wechat.appid")
    private String appid;

    @Value("wechat.pay.notifyUrl")
    private String notifyUrl;

    public ExternalWeChatPayServiceImpl() {
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(privateKey);
        X509Certificate wechatPayCertificate = PemUtil.loadCertificate(
                new ByteArrayInputStream(certificate.getBytes(StandardCharsets.UTF_8)));
        ArrayList<X509Certificate> listCertificates = new ArrayList<>();
        listCertificates.add(wechatPayCertificate);
        httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
                .withWechatPay(listCertificates)
                .build();
    }

    @Override
    public String pay(String openId, List<Order> orders) {
        if (orders.size() == 1) {
            Order order = orders.get(0);
            OrderItem item = order.getItems().get(0);
            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi");
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode rootNode = objectMapper.createObjectNode();

            rootNode.put("mchid", mchId)
                    .put("appid", appid)
                    .put("description", item.getGoodsTitle())
                    .put("notify_url", notifyUrl)
                    .put("out_trade_no", order.getId());
            rootNode.putObject("amount")
                    .put("total", order.getPayment());
            rootNode.putObject("payer")
                    .put("openid", openId);
            try {
                objectMapper.writeValue(bos, rootNode);
                httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
                CloseableHttpResponse response = httpClient.execute(httpPost);
                String bodyAsString = EntityUtils.toString(response.getEntity());
            } catch (Exception e) {

            }
        } else {
            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/combine-transactions/jsapi");
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            Order order = orders.get(0);
            OrderItem item = order.getItems().get(0);
            String trade_no = order.getId();

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("combine_mchid", mchId)
                    .put("combine_appid", appid)

                    .put("notify_url", notifyUrl)
                    .put("combine_out_trade_no", trade_no);
            ArrayNode sub_orders = rootNode.putArray("sub_orders");
            for (Order o : orders) {
                ObjectNode att = objectMapper.createObjectNode();
                att.put("mchid", mchId);
                att.putObject("amount").put("total_amount", o.getPayment());
                att.put("description", item.getGoodsTitle());
                sub_orders.add(att);
            }
            rootNode.putObject("combine_payer_info")
                    .put("openid", openId);
            try {
                objectMapper.writeValue(bos, rootNode);
                httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
                CloseableHttpResponse response = httpClient.execute(httpPost);
                String bodyAsString = EntityUtils.toString(response.getEntity());
            } catch (Exception e) {

            }
        }
        return null;
    }
}
