package com.lh.springboot03.service;

import com.lh.springboot03.bean.User;

public interface UserService {
    //用户注册
    void register(User user,String verifyCodeToRegister,String registerEmail);

    //检查用户名是否存在
    void verifyUsername(String username);

    //检查邮箱是否已注册
    void verifyEmail(String email);

    //根据用户名查询用户，判断是否重名，判断密码是否正确,向redis存入access_token
    User login(String username,String password,String access_token);

    //根据token查询用户信息
    User getTokenValue(String access_token);

    //根据token查询用户信息（直接返回值，不对返回值进行判断）
    User getTokenValueDirect(String access_token);

    //根据用户uid注销用户
    @Deprecated
    boolean loginOut(Integer uid);

    //根据用户token注销用户,断开websocket连接
    void logout(String token, Integer uid);

    //查询用户的验证码是否有效,以及剩余有效时间
    Long isAvailable(String tokenUid);

    //根据uid修改密码
    Integer updatePassword(Integer uid,String oldPassword,String newPassword);

    //找回密码
    void findPassword(User user, String newPassword);

    /**
     * 修改用户的头像
     * @param uid 用户id
     * @param avatar 用户头像路径
     * @param username 用户名称
     */
    void changeAvatar(Integer uid,String avatar,String username);

    //根据用户名查询用户
    User selectUserByUsername(String username);

    //根据uid查询用户
    User selectUserByUid(Integer uid);

    //根据邮箱查询用户
    User selectUserByEmail(String email);

    /**
     * 修改个人信息
     * @param username 用户名
     * @param phone 手机
     * @param qq qq号
     * @param message 个人信息
     * @param gender 性别
     * @param token token令牌
     * @param uid 用户id
     */
    void changeInfo(String username,String phone,String qq, String message,Integer gender,String token,Integer uid);

    //根据uid更改绑定邮箱
    void changeEmail(Integer uid,String access_token);


    long changeModified(String modifiedUser, Integer uid);

    //改变redis中key的过期时间
    Boolean changeExpireTime(String key,Integer timeoutMin);

    //查询redis中key的value，并返回
    String queryRedisValue(String key);

    //根据token和用户uid，修改redis中token的值
    void renewTokenValue(String token,Integer uid);


}
