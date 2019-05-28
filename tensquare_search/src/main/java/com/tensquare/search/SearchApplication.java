package com.tensquare.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

@SpringBootApplication
@RefreshScope
public class SearchApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(SearchApplication.class, args);
    }
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1, 1);
    }

}
