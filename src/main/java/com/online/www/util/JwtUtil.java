package com.online.www.util;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author GoodBoyCoder
 * @date 2021-11-19
 */
@Component
public class JwtUtil {
    private final static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private static Algorithm algorithm;
    private static final String IS_USER = "GoodBoy";

    public JwtUtil() {
        RSA256Key rsa256Key = SecretKeyUtil.getRsa256Key();
        algorithm = Algorithm.RSA256(rsa256Key.getPublicKey(), rsa256Key.getPrivateKey());
    }

    public String creatToken(Object data, long expire) {
        return JWT.create()
                .withIssuer(IS_USER)
                .withAudience(data.toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expire))
                .withClaim("data", JSONObject.toJSONString(data))
                .sign(algorithm);
    }

    public Object verifierToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(IS_USER)
                .build();
        try {
            //验证Token，verifier自动验证
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("data").as(Object.class);
        } catch (JWTVerificationException e) {
            logger.info("token验证失败", e);
        }
        return null;
    }
}
