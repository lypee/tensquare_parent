package com.tensquare.friend.interceptor;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil  ;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器被经过");
        //无论如何都放行 但是能不能操作要在具体业务中判断
        //拦截器只负责把头请求头中包含token的令牌进行解析验证
        String header = request.getHeader("Authorization");
        if( ! StringUtils.isBlank(header)){
            if (header.startsWith("Bearer ")) {
                //得到token
                String token = header.substring(7);
                //对令牌进行验证
                try{
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if (roles != null && roles.equals("admin")) {
                        //为request添加claims_admin属性
                        request.setAttribute("claims_admin" , token);
                    }
                    if (roles != null && roles.equals("user")) {
                        request.setAttribute("claims_user", token);
                    }
                }catch (Exception e)
                {
                    throw new RuntimeException("令牌不正确");
                }

            }
        }
        return true ;
    }
}
