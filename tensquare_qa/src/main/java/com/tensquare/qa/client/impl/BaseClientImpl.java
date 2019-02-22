package com.tensquare.qa.client.impl;

import com.tensquare.qa.client.IBaseClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @author LiangDong.
 */
@Component
public class BaseClientImpl implements IBaseClient {
    @Override
    public Result findById(String labelId) {
        return new Result(false, StatusCode.ERROR, "熔断器启动了");
    }
}
