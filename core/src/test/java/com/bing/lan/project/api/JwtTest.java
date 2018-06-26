package com.bing.lan.project.api;

import org.junit.Test;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * Created by 蓝兵 on 2018/6/26.
 */

public class JwtTest {

    @Test
    public void testJwt() {
        //http://www.cnblogs.com/softidea/p/7041532.html

        Key key = MacProvider.generateKey();
        System.out.println("testJwt() key: " + key);

        String token = Jwts.builder()
                .setSubject("Joe")
                .setExpiration(new Date(System.currentTimeMillis() + 1000000000))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        System.out.println("testJwt() token: " + token);

        JwtParser jwtParser = Jwts.parser().setSigningKey(key);
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        String subject = body.getSubject();
        Date expiration = body.getExpiration();

        System.out.println("testJwt() claimsJws: " + claimsJws);
        System.out.println("testJwt() body: " + body);
        System.out.println("testJwt() subject: " + subject);
        System.out.println("testJwt() expiration: " + expiration);

        assert subject.equals("Joe");
    }
}
