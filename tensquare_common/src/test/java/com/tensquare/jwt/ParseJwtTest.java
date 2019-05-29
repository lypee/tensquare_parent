package com.tensequare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

public class ParseJwtTest {
    public static void main(String[] args) {
        Claims claims = Jwts.parser()
                .setSigningKey("lypee")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMTMyODMxNzYyNzMzNDY5Njk2Iiwic3ViIjoibHlwZWUiLCJpYXQiOjE1NTkwNDQ1NTEsInJvbGVzIjoiYWRtaW4iLCJleHAiOjE1NTkwNTA1NTF9.k9jRgrr7AIiHj1iSAK_4B3ThnCelcLe5YlbM7YjNvyg")
                .getBody();
        System.out.println("用户id："+claims.getId());
        System.out.println("用户名："+claims.getSubject());
        System.out.println("登录时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("过期时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
        System.out.println("用户角色："+claims.get("role"));
    }
}
