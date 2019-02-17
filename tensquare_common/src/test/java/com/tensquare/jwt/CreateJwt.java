package com.tensquare.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author LiangDong.
 */
public class CreateJwt {
    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("2") //登录用户ID
                .setSubject("lisi") //登录用户名
                .setIssuedAt(new Date()) //登录日期
                .signWith(SignatureAlgorithm.HS256, "itcast") //编码 盐
                .claim("role","admin")
                .setExpiration(new Date(new Date().getTime() + 60000));
        String token = jwtBuilder.compact();
        System.out.println(token);
    }
}
