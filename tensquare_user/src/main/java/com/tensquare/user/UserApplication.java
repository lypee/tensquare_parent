package com.tensquare.user;

import com.tensquare.user.interceptor.JwtInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import util.IdWorker;
import util.JwtUtil;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.tensquare.user.interceptor" , "com.tensquare.user.config" , "com.tensqu"})
//@EnableEurekaClient
public class UserApplication {
    public static void main(String[]args)
    {
        SpringApplication.run(UserApplication.class);
    }
    @Bean
    public IdWorker idWorker(){

        return new IdWorker(1, 1);
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil() ;
    }

}
