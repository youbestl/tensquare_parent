package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author LiangDong.
 */
@FeignClient("tensquare-user")
public interface IUserClient {

    @RequestMapping(value = "/user/{userid}/{friendid}/{x}", method = RequestMethod.PUT)
    void updatefanscountandfollowcount(@PathVariable("userid") String userid, @PathVariable("friendid") String friendid, @PathVariable("x") Integer x);
}
