package com.card.config;

import com.card.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class        WebConfig implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;

    // 注册拦截器：拦截名片相关操作，放行登录、注册、验证码接口
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(loginInterceptor)
                        .addPathPatterns("/card/**", "/user/updatePwd", "/user/logout")
                        .excludePathPatterns("/user/login", "/user/register", "/user/checkUname", "/validateCode");
            }

    // 配置静态资源访问（解决上传文件无法直接访问的问题）
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/src/main/resources/static/upload/");
    }
}