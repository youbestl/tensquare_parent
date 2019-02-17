package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

/**
 * @author LiangDong.
 */
public class ParseJwt {
    public static void main(String[] args) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyIiwic3ViIjoibGlzaSIsImlhdCI6MTU0Nzk2NjQyMywicm9sZSI6ImFkbWluIiwiZXhwIjoxNTQ3OTY2NDgzfQ.BskFrZNnEqNlnyooeWF4EUDxUIwXrNgXorzEjqgcNwY";
        Claims claims = Jwts.parser().setSigningKey("itcast").parseClaimsJws(jwt).getBody();
        System.out.println("用户id：" + claims.getId());
        System.out.println("用户名：" + claims.getSubject());
        System.out.println("登录时间：" + sf.format(claims.getIssuedAt()));
        System.out.println("过期时间：" + sf.format(claims.getExpiration()));
        System.out.println("用户角色：" + claims.get("role"));
    }
}
