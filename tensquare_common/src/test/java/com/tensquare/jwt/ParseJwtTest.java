package com.tensequare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

public class ParseJwtTest {
    public static void main(String[] args) {
        Claims claims = Jwts.parser()
                .setSigningKey("lypee")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMTMyOTc4MDY0NTQ0ODk0OTc2Iiwic3ViIjoiMTM5ODAyNzA4MDAiLCJpYXQiOjE1NTg5NjY1NTcsInJvbGVzIjoidXNlciIsImV4cCI6MTU1ODk2NzE1N30.XbY-pD9uIk8m9sFshzYIvok6tq8JPfKRGHcI4aD9Zuc")
                .getBody();
        System.out.println("用户id："+claims.getId());
        System.out.println("用户名："+claims.getSubject());
        System.out.println("登录时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("过期时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
        System.out.println("用户角色："+claims.get("role"));
    }
}
