package com.tensquare.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 网关过滤器
 *
 * @author LiangDong.
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        //pre 可以在请求被路由之前调用
        //route 在路由请求时被调用
        //post 在route和error过滤器之后调用
        //error 处理请求时发生错误时被调用
        return "pre";
    }

    @Override
    public int filterOrder() {
        //多个过滤器的执行顺序，数字越小执行顺序越靠前
        return 0;
    }

    /**
     * 当前过滤器是否开启,true开启 false关闭
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作
     * 返回任何object的值都表示继续执行
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("zuul过滤器...");
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取request域
        HttpServletRequest request = requestContext.getRequest();
        if ("OPTIONS".equals(request.getMethod())) {
            return null;
        }
        if (request.getRequestURL().indexOf("/admin/login") > 0) {
            return null;
        }
        String header = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(header)) {
            try {
                String token = header.substring(7);
                if (header.startsWith("Bearer ")) {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = claims.get("roles") + "";
                    if ("admin".equals(roles)) {
                        //如果具备admin权限，则放行，否者终止执行
                        requestContext.addZuulRequestHeader("Authorization", header);
                        return null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                requestContext.setSendZuulResponse(false); //停止向下运行
            }
        }
        requestContext.setSendZuulResponse(false); //终止运行
        requestContext.setResponseStatusCode(403);
        requestContext.setResponseBody("权限不足");
        requestContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
