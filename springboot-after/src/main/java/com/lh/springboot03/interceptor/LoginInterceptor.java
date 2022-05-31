package com.lh.springboot03.interceptor;

import com.alibaba.fastjson.JSON;
import com.lh.springboot03.bean.Admin;
import com.lh.springboot03.bean.User;
import com.lh.springboot03.utils.JsonResult;
import com.lh.springboot03.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

//解决跨域问题
@CrossOrigin(originPatterns = "*",allowCredentials="true",allowedHeaders = "*")
public class LoginInterceptor implements HandlerInterceptor {
    public LoginInterceptor() {
    }


    public LoginInterceptor(RedisTemplate<String,String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    RedisTemplate<String, User> redisTemplateUser;

    @Autowired
    RedisTemplate<String, Admin> redisTemplateAdmin;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        /**
         * 放行条件
         * 1.请求中存在cookie access_token
         * 2.redis中存在key为access_token值的数据
         */
        if (request.getRequestURI().split("/").length >= 2 &&
        "admin".equals(request.getRequestURI().split("/")[1])){
            String access_token = TokenUtil.getCookieFromRequest(request, "access_token_admin");
            Admin admin;
            if (access_token == null || (admin = redisTemplateAdmin.opsForValue().get(access_token)) == null ){
                System.out.println("已拦截:"+request.getRequestURI());

                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter writer;
                try {
                    writer = response.getWriter();
                    JsonResult<Void> result = new JsonResult<>();
                    result.setMessage("登录已过期，请重新登录(管理员)");
                    result.setState(4010);
                    String jsonString = JSON.toJSONString(result);
                    System.out.println(jsonString);
                    writer.print(jsonString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
            request.setAttribute("admin",admin);
        }else {
            String access_token = TokenUtil.getCookieFromRequest(request, "access_token");
            User user;
            if (access_token == null || (user = redisTemplateUser.opsForValue().get(access_token)) == null ){
                System.out.println("已拦截:"+request.getRequestURI());

                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter writer;
                try {
                    writer = response.getWriter();
                    JsonResult<Void> result = new JsonResult<>();
                    result.setMessage("登录已过期，请重新登录");
                    result.setState(4010);
                    String jsonString = JSON.toJSONString(result);
                    System.out.println(jsonString);
                    writer.print(jsonString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
            request.setAttribute("user",user);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        System.out.println("渲染后"+request.getRequestURI());
//        request.setCharacterEncoding("utf-8");
//        response.setCharacterEncoding("utf-8");

    }
}
