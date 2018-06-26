package com.bing.lan.utils;

import com.bing.lan.core.api.LogUtil;

import java.security.Key;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by 蓝兵 on 2018/6/26.
 */

public class JavaWebTokenUtil {

    protected static final LogUtil log = LogUtil.getLogUtil(JavaWebTokenUtil.class, LogUtil.LOG_VERBOSE);

    private static Key key;

    private static Key getKeyInstance() {
        if (key == null) {
            byte[] apiKeySecretBytes = DatatypeConverter
                    .parseBase64Binary("com.bing.lan.1225");
            key = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        }
        return key;
    }

    //使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
    public static String createJavaWebToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();
    }

    //解析Token，同时也能验证Token，当验证失败返回null
    public static Map<String, Object> parserJavaWebToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getKeyInstance())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.e("json web token verify failed");
            return null;
        }
    }
}