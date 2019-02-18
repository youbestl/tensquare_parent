package com.tensquare.friend.controller;

import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LiangDong.
 */

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    /**
     * 添加好友或者添加非好友
     *
     * @return
     */
    @RequestMapping(value = "/like/{friendid}/{type}", method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid, @PathVariable String type) {
        //验证是否登录，并且拿到当前登录的用户id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims == null) {
            //说明用户权限不足，没有添加好友的权限
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
        //获取用户id
        String userid = claims.getId();
        if (type != null) {
            //判断添加好友，还是添加非好友
            if ("1".equals(type)) {
                //添加好友
                int flag = friendService.addFriend(userid, friendid);
                if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加好友");
                } else if (flag == 1) {
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            } else if ("2".equals(type)) {
                //添加非好友

            }
        }
        return new Result(false, StatusCode.ERROR, "参数异常");
    }

}
