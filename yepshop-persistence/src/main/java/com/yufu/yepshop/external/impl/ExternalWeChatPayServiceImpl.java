package com.yufu.yepshop.external.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yufu.yepshop.external.ExternalWeChatPayService;
import com.yufu.yepshop.types.dto.OrderDTO;
import com.yufu.yepshop.types.dto.OrderItemDTO;
import com.yufu.yepshop.types.dto.TradeDTO;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @author wang
 * @date 2022/1/13 23:32
 */
@Service
public class ExternalWeChatPayServiceImpl implements ExternalWeChatPayService {

//    private final CloseableHttpClient httpClient;

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
//        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(privateKey);
//        X509Certificate wechatPayCertificate = PemUtil.loadCertificate(
//                new ByteArrayInputStream(certificate.getBytes(StandardCharsets.UTF_8)));
//        ArrayList<X509Certificate> listCertificates = new ArrayList<>();
//        listCertificates.add(wechatPayCertificate);
//        httpClient = WechatPayHttpClientBuilder.create()
//                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
//                .withWechatPay(listCertificates)
//                .build();
    }

    @Override
    public String pay(List<TradeDTO> trades) {
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

            rootNode.put("mchid", mchId)
                    .put("appid", appid)
                    .put("description", item.getGoods().getTitle())
                    .put("notify_url", notifyUrl)
                    .put("out_trade_no", order.getId());
            rootNode.putObject("amount")
                    .put("total", order.getPayment());
            rootNode.putObject("payer")
                    .put("openid", trade.getOpenId());
            try {
                objectMapper.writeValue(bos, rootNode);
                httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
//                CloseableHttpResponse response = httpClient.execute(httpPost);
//                String bodyAsString = EntityUtils.toString(response.getEntity());
            } catch (Exception e) {

            }
        } else {
            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/combine-transactions/jsapi");
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            TradeDTO trade = trades.get(0);
            OrderDTO order = trade.getOrder();
            OrderItemDTO item = order.getItems().get(0);
            String tradeNo = order.getId();

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("combine_mchid", mchId)
                    .put("combine_appid", appid)

                    .put("notify_url", notifyUrl)
                    .put("combine_out_trade_no", tradeNo);
            ArrayNode subOrders = rootNode.putArray("sub_orders");
            for (TradeDTO o : trades) {
                ObjectNode att = objectMapper.createObjectNode();
                att.put("mchid", mchId);
                att.putObject("amount").put("total_amount", o.getOrder().getPayment());
                att.put("description", item.getGoods().getTitle());
                subOrders.add(att);
            }
            rootNode.putObject("combine_payer_info")
                    .put("openid", trade.getOpenId());
            try {
                objectMapper.writeValue(bos, rootNode);
                httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
//                CloseableHttpResponse response = httpClient.execute(httpPost);
//                String bodyAsString = EntityUtils.toString(response.getEntity());
            } catch (Exception e) {

            }
        }
        return null;
    }
}
