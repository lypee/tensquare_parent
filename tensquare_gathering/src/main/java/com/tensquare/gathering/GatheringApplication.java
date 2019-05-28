package com.tensquare.gathering;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
@RefreshScope
@SpringBootApplication
public class GatheringApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(GatheringApplication.class, args);
    }
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1, 1);
    }
}
