package com.tju.csdnmanger.config;

import com.tju.csdnmanger.filter.CORSInterceptor;
import com.tju.csdnmanger.filter.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * WebConfig类
 *
 * @author 赵云
 * @date 2020/09/03
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private CORSInterceptor corsInterceptor;

    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //默认不登录即可访问的路径
        String[] unLoginPaths=new String[]{
                "/admin/login",
                "/admin/register",
                "/admin/getCode",
                "/admin/refresh",
                "/admin/forget",
                "/error",
                "/admin/register/getCode",
                "/admin/forget/getCode"
        };
        // addPathPatterns("/**") 表示拦截所有的请求，
        // excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
        //路径前加/
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(unLoginPaths);
        // super.addInterceptors(registry);    //较新Spring Boot的版本中这里可以直接去掉，否则会报错


    }

   /* @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 解决controller返回字符串中文乱码问题
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter)converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        }
    }*/
}
