package com.temsquare.qa.client.impl;


import com.temsquare.qa.client.BaseClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * 熔断器类 可以记录日志
 */
@Component
public class BaseClientImpl  implements BaseClient {
    @Override
    public Result findById(String labelId) {
        return new Result(false, StatusCode.ERROR, "熔断器触发了！");
    }
}

