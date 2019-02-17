package com.tensquare.user.aop;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LiangDong.
 */

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 在方法之前进行拦截，负责把有请求头中包含token的令牌进行一个解析验证
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String authorization = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(authorization)) {
            if (authorization.startsWith("Bearer ")) {
                //获取token
                String token = authorization.substring(7);
                //解析token
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    final String roles = claims.get("roles") + "";
                    if (roles != null && roles.equals("admin")) {
                        //将具有admin权限的token存入request中
                        request.setAttribute("claims_admin", token);
                    }
                    if (roles != null && roles.equals("user")) {
                        //将具有普通权限的token存入request中
                        request.setAttribute("claims_user", token);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("token有误!");
                }

            }
        }
        return true;
    }
}
