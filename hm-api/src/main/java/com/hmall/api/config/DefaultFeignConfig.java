package com.hmall.api.config;

import com.hmall.api.client.fallback.ItemClientFallbackFactory;
import com.hmall.common.interceptors.UserInfoInterceptor;
import com.hmall.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.aopalliance.intercept.Interceptor;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
    @Bean
    public RequestInterceptor userInfoInterceptor() {
        return new RequestInterceptor(){
                @Override
                public void apply(RequestTemplate template) {
                    Long userInfo = UserContext.getUser();
                    if (userInfo != null) {
                        template.header("userInfo", userInfo.toString());
                    }
                }
        };
    }

    @Bean
    public ItemClientFallbackFactory itemClientFallbackFactory() {
        return new ItemClientFallbackFactory();
    }
}
