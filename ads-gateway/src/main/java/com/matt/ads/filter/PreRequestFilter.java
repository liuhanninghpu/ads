package com.matt.ads.filter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter{
    //过滤器类型
    @Override
    public String filterType(){
        return FilterConstants.PRE_TYPE;
    }

    //过滤优先级
    @Override
    public int filterOrder(){
        return 0;
    }

    //是否开启
    @Override
    public boolean shouldFilter(){
        return true;
    }

    @Override
    public Object run() throws ZuulException{
        RequestContext ctx = RequestContext.getCurrentContext();
        //记录当前时间戳
        ctx.set("startTime",System.currentTimeMillis());
        return null;
    }

}
