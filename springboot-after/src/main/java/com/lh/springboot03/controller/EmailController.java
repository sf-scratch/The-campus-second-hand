package com.lh.springboot03.controller;

import com.lh.springboot03.bean.User;
import com.lh.springboot03.service.EmailService;
import com.lh.springboot03.service.UserService;
import com.lh.springboot03.utils.JsonResult;
import com.lh.springboot03.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/emails")
public class EmailController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;


    @RequestMapping("/registerByEmail")
    public JsonResult<Long> registerByEmail(@RequestParam("registerEmail") String registerEmail,
                                            HttpServletResponse response, HttpServletRequest request){
        System.out.println(registerEmail);
        JsonResult<Long> result = new JsonResult<>();
        String cookieFromRequest = TokenUtil.getCookieFromRequest(request, "registerEmail");
        registerEmail = cookieFromRequest == null || registerEmail != null ? registerEmail : cookieFromRequest;

        Long remainTime = registerEmail == null ? null : userService.isAvailable(registerEmail);
        if (registerEmail != null && remainTime == -2){
            //检查邮箱是否已注册
            userService.verifyEmail(registerEmail);
            remainTime = emailService.sendVerificationCodeByEmail(registerEmail, registerEmail);

            //为该用户标记一个name为registerEmail的cookie，目的是使其在成功发送注册验证码的120秒内不能再注册其他邮箱（比较鸡肋，后续可改进）
            Cookie cookie = new Cookie("registerEmail",registerEmail);
            cookie.setPath("/");
            cookie.setMaxAge(120);
            response.addCookie(cookie);

            result.setState(OK);
            result.setMessage("验证码已发送到指定邮箱");
        }else {
            result.setState(6021);
            result.setMessage("验证码冷却中");
        }
        result.setData(remainTime);
        return result;
    }


    @RequestMapping("/findPasswordByEmail")
    public JsonResult<Long> findPasswordByEmail(@RequestParam("registerEmail") String registerEmail){
        System.out.println(registerEmail);
        JsonResult<Long> result = new JsonResult<>();
        User user = userService.selectUserByEmail(registerEmail);
        String verifyCodeToFindPassword = TokenUtil.getVerifyCodeToFindPassword(user.getUid());
        Long remainTime = userService.isAvailable(verifyCodeToFindPassword);
        if (remainTime == -2){
            remainTime = emailService.sendVerificationCodeByEmail(registerEmail, verifyCodeToFindPassword);
            result.setState(OK);
            result.setMessage("验证码已发送到指定邮箱");
        }else {
            result.setState(6021);
            result.setMessage("验证码冷却中");
        }
        result.setData(remainTime);
        return result;
    }





    @RequestMapping("/waitTwoMin")
    public JsonResult<Object[]> waitTwoMin(@RequestParam("onlyQuery") Integer onlyQuery,
                                           @RequestParam(value = "newEmail",required = false) String newEmail,
                                           HttpServletRequest request){
        JsonResult<Object[]> jsonResult = new JsonResult<>();
        Object[] data = new Object[2];

        //从请求域中获取 user
        User user = TokenUtil.getUserFromRequest(request);
        Integer uid = user.getUid();



        //根据onlyQuery 将uid或username 转换为redis中存储的验证码的key
        String verifyCode;
        if (Math.abs(onlyQuery) == 1){
            verifyCode = TokenUtil.getVerifyCode(uid);
        }else if (Math.abs(onlyQuery) == 2){
            verifyCode = TokenUtil.getVerifyCodeToChange(uid);
        }else{
            verifyCode = TokenUtil.getVerifyCodeToChange2(uid);
//            String redisNewEmail;
//            if (newEmail == null && (redisNewEmail = userService.queryRedisValue(TokenUtil.getTokenNewEmail(uid))) != null){
//                data[1] = redisNewEmail;
//            }else if (newEmail == null && (redisNewEmail = userService.queryRedisValue(TokenUtil.getTokenNewEmail(uid))) == null){
//                return new JsonResult<>();
//            }
            String redisNewEmail = userService.queryRedisValue(TokenUtil.getTokenNewEmail(uid));
            if (newEmail == null && (redisNewEmail) != null){
                data[1] = redisNewEmail;
            }else if (newEmail == null){
                jsonResult.setMessage("请输入邮箱");
                jsonResult.setState(4015);
                return jsonResult;
            }
        }



        //验证该key是否有效，返回剩余有效期
        Long remainTime = userService.isAvailable(verifyCode);

        if (onlyQuery > 0){//是否刚进入页面做判断
            if (remainTime > 0){//处于验证码冷却中
                jsonResult.setState(2000);
            }else {//可正常获取验证码
                jsonResult.setState(OK);
            }
        }else if (remainTime > 0){
            jsonResult.setState(2000);
            jsonResult.setMessage("验证码冷却中");
        }else if (newEmail != null){
            remainTime = emailService.sendVerificationCodeByEmail(newEmail, verifyCode);
            //将newEmail保存到redis中
            emailService.saveTempNewEmail(uid,newEmail);
            data[1] = newEmail;
            jsonResult.setState(OK);
            jsonResult.setMessage("验证码已发送至指定邮箱");
        }else {
            remainTime = emailService.sendVerificationCodeByEmail(user.getEmail(), verifyCode);
            jsonResult.setState(OK);
            jsonResult.setMessage("验证码已发送至绑定邮箱");
        }


        System.out.println("进入waitTwoMin"+"-"+jsonResult.getState()+"-"+remainTime);
        data[0] = remainTime;
        jsonResult.setData(data);
        return jsonResult;
    }









}
