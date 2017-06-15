package com.lvdun.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2017/6/13.
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

        String[] addPath = {
            "/",
            "/grabListRule/**",
            "/grabDetailRule/**",
            "/appTop/**",
            "/conArticle/**"
        };
        String[] excludePath = {
            "/toLogin",
            "/toDefault"
        };
        LoginInterceptor loginInterceptor1 = new LoginInterceptor();
        registry.addInterceptor(loginInterceptor1).addPathPatterns(addPath).excludePathPatterns(excludePath);
    }
}
