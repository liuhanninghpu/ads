package com.matt.ads.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Component
public class AccessLogFilter extends ZuulFilter {
    //过滤器类型
    @Override
    public String filterType(){
        return FilterConstants.POST_TYPE;
    }

    //过滤优先级
    @Override
    public int filterOrder(){
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    //是否开启
    @Override
    public boolean shouldFilter(){
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        Long startTime = (Long)context.get("startTime");
        String uri = request.getRequestURI();
        long duration = System.currentTimeMillis()-startTime;
        //log.info("uri: "+uri+", duration: "+duration/100+"ms");
        return null;
    }
}
