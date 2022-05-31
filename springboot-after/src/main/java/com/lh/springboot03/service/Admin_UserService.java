package com.lh.springboot03.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.springboot03.bean.Admin;
import com.lh.springboot03.bean.User;
import com.lh.springboot03.bean_admin.UserActiveInfo;

import java.util.List;

public interface Admin_UserService {
    //根据账户名查询管理员，判断密码是否正确,向redis存入access_token
    Admin login(String name, String password, String access_token);

    //根据用户token注销管理员,断开websocket连接
    void logout(String token, Integer id);

    //获取用户列表
    List<User> getUserList();

    //获取用户活跃信息
    UserActiveInfo getUserActiveInfo();

    //改变用户的是否删除状态
    void updateIsDelete(Integer uid, Integer isDelete, String modifiedUser);

    /**
     * 更改用户信息
     */
    void updateUserInfo(User user);

    /**
     * 根据uid改用户数据
     * @param uid 用户id
     */
    void deleteUserByUid(Integer uid);

    /**
     *分页搜索用户列表
     * @param currentPage 当前页数
     * @param size 每页数量
     * @param keyword 关键词
     * @return Mybatis-plus封装的Page
     */
    Page<User> searchUserListByPage(Integer currentPage, Integer size, String keyword);

}
