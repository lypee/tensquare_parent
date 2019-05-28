package com.tensquare.article;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

@SpringBootApplication
@RefreshScope
//@EnableEurekaClient
public class ArticleApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(ArticleApplication.class);
    }
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1, 1);
    }
}
