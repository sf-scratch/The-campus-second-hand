package com.lh.springboot03.config;

import com.lh.springboot03.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//    自定义拦截对象
//        HandlerInterceptor loginInterceptor = new LoginInterceptor();
//    白名单
        List<String> patterns = new ArrayList<>();
//        patterns.add("/");
//        patterns.add("/index.html");
//        patterns.add("/web/product.html");
//        patterns.add("/web/index.html");
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/login.html");
        patterns.add("/web/register.html");
        patterns.add("/users/register");
        patterns.add("/emails/registerByEmail");
        patterns.add("/emails/findPasswordByEmail");
        patterns.add("/users/login");
        patterns.add("/users/loginVue");//////////////
        patterns.add("/users/aa");/////////、、、、
        patterns.add("/users/verifyUsername");
        patterns.add("/users/findPassword");
        patterns.add("/error");
        patterns.add("/favicon.ico");
        patterns.add("/goods/home/*");
        patterns.add("/comment/home/*");
        patterns.add("/upload/**");
        patterns.add("/alipay/**");
        patterns.add("/admin/users/login");
//        patterns.add("/upload/goodsImages/*");
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns(patterns);

//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").
//                excludePathPatterns("/","/login","/css/**","/fonts/**","/images/**","/js/**","/error");
    }
}
