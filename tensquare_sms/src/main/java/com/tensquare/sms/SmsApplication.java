package com.tensquare.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class SmsApplication {
    public static void main(String []args)
    {
        SpringApplication.run(SmsApplication.class);
    }
}
