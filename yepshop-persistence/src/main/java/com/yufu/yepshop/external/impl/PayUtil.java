package com.yufu.yepshop.external.impl;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.*;
import java.util.*;

/**
 * @author wang
 * @date 2022/5/9 21:52
 */
public class PayUtil {
    public static String makeUuid(int len) {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, len);
    }

    public static String getCurrentTimeStamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    public static String sign(byte[] message, PrivateKey privateKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        //签名方式
        Signature sign = Signature.getInstance("SHA256withRSA");
        //私钥，通过MyPrivateKey来获取，这是个静态类可以接调用方法 ，需要的是_key.pem文件的绝对路径配上文件名
        sign.initSign(privateKey);
        sign.update(message);

        return Base64.getEncoder().encodeToString(sign.sign());
    }
}
