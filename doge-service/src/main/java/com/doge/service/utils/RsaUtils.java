package com.doge.service.utils;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * RSA操作类
 *
 * @author shixinyu
 * @date 2023/2/28 18:23
 */
@Component
@ConfigurationProperties(prefix = "rsa")
public class RsaUtils {
    private static String privateKey;

    public void setPrivateKey(String privateKey) {
        RsaUtils.privateKey = privateKey;
    }

    /**
     * RSA解密
     *
     * @param cipherData 密文
     * @return java.lang.String 明文
     */
    public static String decrypt(String cipherData) {
        RSA rsa = new RSA(privateKey, null);
        return rsa.decryptStr(cipherData, KeyType.PrivateKey);
    }
}