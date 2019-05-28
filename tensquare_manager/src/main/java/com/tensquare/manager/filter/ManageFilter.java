package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

public class ManageFilter extends ZuulFilter {
    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        return null;
    }

    /**
     * 在请求前或者后  q前写pre , 后写post 过滤
     * @return
     */
    @Override
    public String filterType() {
        return null;
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
