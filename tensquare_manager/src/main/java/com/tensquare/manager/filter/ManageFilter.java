package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManageFilter extends ZuulFilter {
    @Autowired
    private JwtUtil jwtUtil ;
    /**
     * 当前过滤器是否开启
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作 , return 任何Object
     * 的值 都表示继续执行
     * 如果不再继续执行 则
     * setsendzullResponse(fasle) 表示不再继续执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过ZuulFilter");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if (request.getRequestURI().indexOf("login") > 0  || StringUtils.equals(request.getMethod(), "OPTIONS")) {
            return null;
        }
        String header = request.getHeader("Authorization");
        if(!StringUtils.isBlank(header))
        {
            String token = header.substring(7) ;
            try{
                Claims claims = jwtUtil.parseJWT(token);
                String roles = (String) claims.get("roles");
                if (StringUtils.equals("admin" , roles)) {
                    //头信息转发下去且放行
                    requestContext.addZuulRequestHeader("Authorization" , header);
                    return null ;
                }
            }catch (Exception e)
            {
                e.printStackTrace();
                //终止运行
                requestContext.setSendZuulResponse(false);
            }
        }
        requestContext.setSendZuulResponse(false);//终止运行
        requestContext.setResponseStatusCode(403);
        requestContext.setResponseBody("权限不足");
        requestContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }

    /**
     * 在请求前或者后  q前写pre , 后写post 过滤
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器由小到大的优先级
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }
}
