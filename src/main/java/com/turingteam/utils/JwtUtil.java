package com.turingteam.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT工具类
 */
public class JwtUtil {
    /**
     * 密钥
     */
    private static final String SECRET_KEY = "";

    /**
     * 过期时间
     */
    private static final long EXPIRE_TIME = 864000000;

    /**
     * 生成token
     * @param subject token中要存放的数据
     * @return token
     */
    public static String generateToken(String subject) {
        Date now  = new Date();

        return Jwts.builder()
                .setId(String.valueOf(UUID.randomUUID()))
                .setSubject(subject)
                .setIssuedAt(now)
                .setIssuer("")
                .setExpiration(new Date(now.getTime() + EXPIRE_TIME))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * 从token中解析出subject
     * @param token token
     * @return subject
     */
    public static String extractSubject(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * 判断token是否过期
     * @param token token
     * @return 是否过期， true：过期|false：未过期
     */
    public static boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    /**
     * 判断token是否有效
     * @param token token
     * @return 是否有效， true：有效|false：无效
     */
    public static boolean isTokenEffective(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

