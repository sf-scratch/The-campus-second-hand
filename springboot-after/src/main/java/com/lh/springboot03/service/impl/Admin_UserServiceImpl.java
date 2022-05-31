package com.lh.springboot03.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.springboot03.bean.Admin;
import com.lh.springboot03.bean.User;
import com.lh.springboot03.bean_admin.UserActiveInfo;
import com.lh.springboot03.mapper.Admin_AdminMapper;
import com.lh.springboot03.mapper.Admin_GoodsMapper;
import com.lh.springboot03.mapper.Admin_UserMapper;
import com.lh.springboot03.mapper.UserMapper;
import com.lh.springboot03.service.Admin_UserService;
import com.lh.springboot03.service.exception.*;
import com.lh.springboot03.utils.MD5Util;
import com.lh.springboot03.utils.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class Admin_UserServiceImpl implements Admin_UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    Admin_AdminMapper admin_adminMapper;
    @Autowired
    Admin_UserMapper admin_userMapper;
    @Autowired
    Admin_GoodsMapper admin_goodsMapper;
    @Autowired
    private RedisTemplate<String, Admin> redisTemplateAdmin;
    //登录过期时间（min）
    private static final int overdueTime = 4146;

    @Override
    public Admin login(String name, String password, String access_token) {
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("name",name);
        List<Admin> adminList = admin_adminMapper.selectList(adminQueryWrapper);
        if (adminList.size() == 0){
            //账户不存在
            throw new UserNotFoundException("账户不存在");
        }
        Admin admin = adminList.get(0);
        if (!(MD5Util.getMD5andSalt(password,admin.getSalt()).equals(admin.getPassword()))){
            //账户名或密码错误
            throw new PasswordNotMatchException("账户名或密码错误");
        }

        redisTemplateAdmin.opsForValue().set(access_token,admin,overdueTime, TimeUnit.MINUTES);

        System.out.println("已存redis-key:"+access_token+"value:"+admin);
        return admin;
    }

    @Override
    public void logout(String token, Integer id) {
        redisTemplateAdmin.delete(token);
        Session session = WebSocketUtil.ADMIN_ONLINE_SESSION.get(id);
        if (session != null){
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getUserList() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        return admin_userMapper.selectList(userQueryWrapper.orderByDesc("created_time "));
    }

    @Override
    public UserActiveInfo getUserActiveInfo() {
        UserActiveInfo userActiveInfo = new UserActiveInfo();
        userActiveInfo.setCurrentMouthRegisterUser(admin_userMapper.getCurrentMouthRegisterUser());
        userActiveInfo.setUserSum(admin_userMapper.selectCount(null));
        userActiveInfo.setCurrentMouthReleaseGoodsSum(admin_goodsMapper.getCurrentMouthReleaseGoodsSum());
        userActiveInfo.setCurrentDayReleaseGoodsSum(admin_goodsMapper.getCurrentDayReleaseGoodsSum());
        userActiveInfo.setGoodsSum(admin_goodsMapper.selectCount(null));
        return userActiveInfo;
    }

    @Override
    public void updateIsDelete(Integer uid, Integer isDelete, String modifiedUser) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        User user = new User();
        user.setIsDelete(isDelete);
        user.setModifiedUser("admin_" + modifiedUser);
        user.setModifiedTime(new Date());
        int update = userMapper.update(user, userQueryWrapper.eq("uid",uid));
        if (update != 1){
            throw new UpdateIsDeleteException("更改用户是否删除异常");
        }
    }

    @Override
    public void updateUserInfo(User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        int update = userMapper.update(user, userQueryWrapper.eq("uid", user.getUid()));
        if (update != 1){
            throw new UpdateInfoException("更新用户信息产生未知异常");
        }
    }

    @Override
    public void deleteUserByUid(Integer uid) {
        int delete = userMapper.deleteById(uid);
        if (delete != 1){
            throw new DeleteUserException("删除用户产生未知异常");
        }
    }

    @Override
    public Page<User> searchUserListByPage(Integer currentPage, Integer size, String keyword) {
        Page<User> userPage = new Page<>(currentPage,size);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (keyword != null){
            userQueryWrapper.like("username", "%" + keyword + "%");
        }
        //调用getRecords，取出集合数据
        userMapper.selectPage(userPage, userQueryWrapper.orderByDesc("created_time "));
//        System.out.println("search获取当前页:"+goodsPage.getCurrent());
//        System.out.println("search获取总记录数:"+goodsPage.getTotal());
//        System.out.println("search获取每页的条数:"+goodsPage.getSize());
//        System.out.println("search获取每页数据的集合:"+goodsPage.getRecords());
//        System.out.println("search获取总页数:"+goodsPage.getPages());
//        System.out.println("search是否存在下一页:"+goodsPage.hasNext());
//        System.out.println("search是否存在上一页:"+goodsPage.hasPrevious());
        return userPage;
    }

}
