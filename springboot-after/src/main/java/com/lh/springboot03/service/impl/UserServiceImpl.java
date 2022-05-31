package com.lh.springboot03.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lh.springboot03.bean.User;
import com.lh.springboot03.mapper.UserMapper;
import com.lh.springboot03.service.CommentService;
import com.lh.springboot03.service.UserService;
import com.lh.springboot03.service.exception.*;
import com.lh.springboot03.utils.MD5Util;
import com.lh.springboot03.utils.TokenUtil;
import com.lh.springboot03.utils.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private RedisTemplate<String,User> redisTemplateUser;
    @Autowired
    private CommentService commentService;
    //登录过期时间（min）
    private static final int overdueTime = 4146;
    @Override
    public void register(User user,String verifyCodeToRegister,String registerEmail) {
        String username = user.getUsername();
        //检查用户名是否占用
        verifyUsername(username);


        //检查验证码是否正确
        String curVerifyCodeToRegister = redisTemplate.opsForValue().get(registerEmail);
        if (curVerifyCodeToRegister == null){
            throw new InsertException("请获取验证码");
        }else if (!curVerifyCodeToRegister.equals(verifyCodeToRegister)){
            throw new InsertException("验证码错误");
        }

        //对密码进行加密处理: MD5算法  和  随机数+密码+随机数
        //处理方式：md5对密码，连续加密三次
        String oldPassword = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = MD5Util.getMD5andSalt(oldPassword,salt);
        user.setPassword(md5Password);
        user.setSalt(salt);
        //补全数据
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        user.setEmail(registerEmail);
        int rows = userMapper.insert(user);
        redisTemplate.delete(registerEmail);
        if (rows != 1){
            throw new InsertException("用户注册过程中产生未知异常");
        }
    }

    //检查邮箱是否已注册
    @Override
    public void verifyEmail(String email) {
        Integer integer = userMapper.verifyEmail(email);
        if (integer > 0){
            throw new EmailDuplicatedException("该邮箱已被注册");
        }
    }

    @Override
    public void verifyUsername(String username) {
        User result = userMapper.findByUsername(username);
        if (result != null){
            throw new UsernameDuplicatedException("用户名被占用");
        }
    }



    @Override
    public User login(String username,String password,String access_token) {
        User user = userMapper.findByUsername(username);
        if (user == null || user.getIsDelete().equals(1)){
//            用户名不存在
            throw new UserNotFoundException("用户不存在");
        }
        if (!(MD5Util.getMD5andSalt(password,user.getSalt()).equals(user.getPassword()))){
//            密码错误
            throw new PasswordNotMatchException("用户名或密码错误");
        }
        redisTemplateUser.opsForValue().set(access_token,user,overdueTime,TimeUnit.MINUTES);
        System.out.println("已存redis-key:"+access_token+"value:"+user);
        return user;
    }

    @Override
    public User getTokenValue(String access_token) {
        User user = redisTemplateUser.opsForValue().get(access_token);
        if (user == null || user.getIsDelete().equals(1)){
            throw new UserNotFoundException("登录过期");
        }
        return user;
    }

    @Override
    public User getTokenValueDirect(String access_token) {
        return redisTemplateUser.opsForValue().get(access_token);
    }


    @Override
    public boolean loginOut(Integer uid) {
        Boolean delete = redisTemplate.delete(TokenUtil.getTokenUid(uid));
        return delete != null && delete;
    }


    @Override
    public void logout(String token, Integer uid) {
        redisTemplate.delete(token);
        Session session = WebSocketUtil.ONLINE_SESSION.get(uid);
        if (session != null){
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Long isAvailable(String tokenUid) {
        return redisTemplate.getExpire(tokenUid);
    }


    @Override
    public Integer updatePassword(Integer uid,String oldPassword,String newPassword) {
        User user = userMapper.findByUid(uid);
        if (user == null || user.getIsDelete().equals(1)){
            throw new UserNotFoundException("用户不存在");
        }
        if (user.getPassword().equals(MD5Util.getMD5andSalt(newPassword,user.getSalt()))){
            throw new PasswordNotMatchException("新密码与旧密码相同");
        }
        if (!(MD5Util.getMD5andSalt(oldPassword,user.getSalt()).equals(user.getPassword()))){
            throw new PasswordNotMatchException("原密码错误");
        }
        return userMapper.updatePasswordByUid(MD5Util.getMD5andSalt(newPassword,user.getSalt()),
                user.getModifiedUser(), new Date(), user.getUid());
    }

    @Override
    public void findPassword(User user, String newPassword) {
        if (user.getPassword().equals(MD5Util.getMD5andSalt(newPassword,user.getSalt()))){
            throw new PasswordNotMatchException("新密码与旧密码相同");
        }
        Integer update = userMapper.updatePasswordByUid(MD5Util.getMD5andSalt(newPassword, user.getSalt()),
                user.getModifiedUser(), new Date(), user.getUid());
        if (update != 1){
            throw new UpdateInfoException("更新用户邮箱产生未知异常");
        }
        List<String> deleteKeys = new ArrayList<>();
        deleteKeys.add(TokenUtil.getVerifyCodeToFindPassword(user.getUid()));
        redisTemplate.delete(deleteKeys);
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User user = userMapper.findByUid(uid);
        if (user == null || user.getIsDelete().equals(1)){
            throw new UserNotFoundException("用户不存在");
        }
        Integer integer = userMapper.updateAvatarByUid(avatar, username, new Date(), uid);
        if (integer != 1){
            throw new UpdateException("更新用户头像产生未知异常");
        }
        commentService.updateAvatarInCommentByUidAndAvatar(uid,avatar);
    }

    @Override
    public User selectUserByUsername(String username) {
        User user = userMapper.findByUsername(username);
        if (user == null){
            throw new UserNotFoundException("用户不存在");
        }
        return user;
    }

    @Override
    public User selectUserByUid(Integer uid) {
        User user = userMapper.findByUid(uid);
        if (user == null){
            throw new UserNotFoundException("用户不存在");
        }
        return user;
    }

    @Override
    public User selectUserByEmail(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",email);
        List<User> userList = userMapper.selectList(queryWrapper);
        if (userList.size() == 0){
            throw new UserNotFoundException("该邮箱未注册");
        }
        return userList.get(0);
    }

    //修改用户数据
    @Override
    public void changeInfo(String username, String phone ,String qq, String message, Integer gender, String token,Integer uid) {
        //更改mysql中用户的数据
        Integer integer = userMapper.updateInfoByUsername(username, phone, qq, message, gender, new Date());
        if (integer != 1){
            throw new UpdateInfoException("更新用户信息产生未知异常");
        }
        //获取更新后的用户信息
        User user = userMapper.findByUid(uid);
        //更改redis中用户的数据

        //注意：redisTemplateUser.opsForValue().set("a",user,0);
        //如果更改的值比之前的短，将会出现问题
        //0指的是 覆盖从指定位置开始的值。
        //这个的覆盖是采用从索引0开始一个一个覆盖，短了的话，后面的将无法覆盖，导致数据出错
        //但是不设置过期时间，那会使该数据没有过期时间
        //解决：
        //redisTemplateUser.opsForValue().set("a",user,redisTemplateUser.getExpire("a"),TimeUnit.SECONDS);

        //这里只是登录的过期时间，不需要非常准确
        redisTemplateUser.opsForValue().set(token,user,overdueTime,TimeUnit.MINUTES);
    }

    @Override
    public void changeEmail(Integer uid,String access_token) {
        String newEmail = redisTemplate.opsForValue().get(TokenUtil.getTokenNewEmail(uid));
        Integer integer = userMapper.updateEmailByUid(uid,newEmail);
        if (integer != 1){
            throw new UpdateInfoException("更新用户邮箱产生未知异常");
        }
        List<String> deleteKeys = new ArrayList<>();
        deleteKeys.add(access_token);
        deleteKeys.add(TokenUtil.getVerifyCode(uid));
        deleteKeys.add(TokenUtil.getVerifyCodeToChange(uid));
        deleteKeys.add(TokenUtil.getVerifyCodeToChange2(uid));
        deleteKeys.add(TokenUtil.getTokenNewEmail(uid));
        redisTemplate.delete(deleteKeys);
    }

    @Override
    public long changeModified(String modifiedUser,Integer uid) {
        if (uid == null){
            throw new UserNotFoundException("登录过期，请重新登录");
        }
        Date date = new Date();
        Integer integer = userMapper.updateModified(modifiedUser, date, uid);
        if (integer != 1){
            throw new UpdateModifiedException("验证信息预处理异常");
        }
        return date.getTime();
    }

    //更改redis中key的过期时间，（min）
    @Override
    public Boolean changeExpireTime(String key, Integer timeoutMin) {
        return redisTemplate.expire(key, timeoutMin, TimeUnit.MINUTES);
    }

    //查询redis中key的value值
    @Override
    public String queryRedisValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    //根据token和用户uid，修改redis中token的值
    @Override
    public void renewTokenValue(String token, Integer uid) {
        //获取更新后的用户信息
        User user = userMapper.findByUid(uid);
        //更改redis中用户的数据
        redisTemplateUser.opsForValue().set(token,user,overdueTime,TimeUnit.MINUTES);;
    }


}
