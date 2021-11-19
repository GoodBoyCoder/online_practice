package com.online.www.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author GoodBoyCoder
 * @date 2021-11-19
 */
public class SecretKeyUtil {
    private final static Logger logger = LoggerFactory.getLogger(SecretKeyUtil.class);
    /**
     * 数字签名
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * RSA密钥长度
     */
    public static final int KEY_SIZE = 1024;
    /**
     * 密钥实例
     */
    private static volatile RSA256Key rsa256Key;

    /**
     * 生成 公钥/私钥
     */
    public static RSA256Key getRsa256Key() {
        if (rsa256Key == null) {
            synchronized (RSA256Key.class) {
                if (rsa256Key == null) {
                    //密钥生成所需的随机数源
                    KeyPairGenerator keyPairGen = null;
                    try {
                        keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
                    } catch (NoSuchAlgorithmException e) {
                        logger.error(e.getMessage());
                        throw new RuntimeException(e);
                    }
                    keyPairGen.initialize(KEY_SIZE);
                    KeyPair keyPair = keyPairGen.generateKeyPair();
                    //获取公钥和私钥
                    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
                    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
                    rsa256Key = new RSA256Key(publicKey, privateKey);
                }
            }
        }
        return rsa256Key;
    }
}
