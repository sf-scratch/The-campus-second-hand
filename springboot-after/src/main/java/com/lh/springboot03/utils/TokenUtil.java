package com.lh.springboot03.utils;


import com.lh.springboot03.bean.Admin;
import com.lh.springboot03.bean.CommentUserInfo;
import com.lh.springboot03.bean.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

public class TokenUtil {

    public static String getTokenUid(Integer uid){
        return uid == null ? null : "loginUserUid:" + uid + ":ip";
    }

    public static String getVerifyCode(Integer uid){
        return uid == null ? null : "loginUserUid:" + uid + ":verifyCode";
    }

    public static String getVerifyCodeToFindPassword(Integer uid){
        return uid == null ? null : "findPasswordUid:" + uid + ":verifyCodeToFindPassword";
    }

    public static String getVerifyCodeToChange(Integer uid){
        return uid == null ? null : "loginUserUid:" + uid + ":verifyCodeToChange";
    }

    public static String getVerifyCodeToChange2(Integer uid){
        return uid == null ? null : "loginUserUid:" + uid + ":verifyCodeToChange2";
    }

    public static String getTokenNewEmail(Integer uid){
        return uid == null ? null : "loginUserUid:" + uid + ":newEmail";
    }

    public static Integer transformTokenInteger(String n){
        return n == null ? null : Integer.parseInt(n.split(":")[1]);
    }



//    获取该请求ip
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length () == 0 || "unknown".equalsIgnoreCase (ip)) {
            ip = request.getHeader ("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length () == 0 || "unknown".equalsIgnoreCase (ip)) {
            ip = request.getRemoteAddr ();
            if (ip.equals ("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost ();
                } catch (Exception e) {
                    e.printStackTrace ();
                }
                ip = inet.getHostAddress ();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length () > 15) {
            if (ip.indexOf (",") > 0) {
                ip = ip.substring (0, ip.indexOf (","));
            }
        }
        return ip;
    }

//    从请求中取出对应的cookie
    public static String getCookieFromRequest(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        String temp = null;
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(cookieName)){
                    temp = cookie.getValue();
                }
            }
        }
        return temp;
    }

    //从请求域中获取access_token
    public static String getTokenFromRequest(HttpServletRequest request){
        return getCookieFromRequest(request,"access_token");
    }

    //从请求域中获取access_token_admin
    public static String getAdminTokenFromRequest(HttpServletRequest request){
        return getCookieFromRequest(request,"access_token_admin");
    }

//    从请求中取出对应的cookie，并将其value转换为Integer类型
    public static Integer getIntegerCookieFromRequest(HttpServletRequest request, String cookieName) {
        String cookieValue = getCookieFromRequest(request, cookieName);
        return transformTokenInteger(cookieValue);
    }


//    从请求域中获取user
    public static User getUserFromRequest(HttpServletRequest request) {
        return (User)request.getAttribute("user");
    }

    //从请求域中获取admin
    public static Admin getAdminFromRequest(HttpServletRequest request) {
        return (Admin)request.getAttribute("admin");
    }

    //从User中取出CommentUserInfo
    public static CommentUserInfo getCommentUserInfoFromUser(User user) {
        CommentUserInfo commentUser = new CommentUserInfo();
        commentUser.setId(user.getUid());
        commentUser.setNickName(user.getUsername());
        commentUser.setAvatar(user.getAvatar());
        return commentUser;
    }
}
