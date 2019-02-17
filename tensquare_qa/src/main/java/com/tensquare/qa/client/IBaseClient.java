package com.tensquare.qa.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author LiangDong.
 */
@FeignClient("tensquare-base")
public interface IBaseClient {

    @RequestMapping(value = "/label/{labelId}", method = RequestMethod.GET)
    Result findById(@PathVariable("labelId") String labelId);
}
