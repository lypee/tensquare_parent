package com.temsquare.qa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
import util.JwtUtil;

@SpringBootApplication
//使该服务能被发现且作为feign的方式被发现
@EnableDiscoveryClient
@EnableFeignClients
@EnableEurekaClient
public class QaApplication {
    public static void main(String[]args)
    {
        SpringApplication.run(QaApplication.class, args);
    }
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1) ;
    }
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil() ;
    }
}
