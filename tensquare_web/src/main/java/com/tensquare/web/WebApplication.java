package com.tensquare.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import util.JwtUtil;

/**
 * 前台网关
 */
@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
@RefreshScope
public class WebApplication {
    public static void main(String []args)
    {
        SpringApplication.run(WebApplication.class);
    }
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil() ;
    }
}
