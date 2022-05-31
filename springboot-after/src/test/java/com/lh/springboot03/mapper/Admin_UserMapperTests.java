package com.lh.springboot03.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lh.springboot03.bean.Admin;
import com.lh.springboot03.bean.User;
import com.lh.springboot03.service.Admin_UserService;
import com.lh.springboot03.service.exception.UsernameDuplicatedException;
import com.lh.springboot03.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

//测试类，不会随项目打包发送
@SpringBootTest
//表示启动这个单元测试类，参数未SpringRunner的实列类型
@RunWith(SpringRunner.class)
public class Admin_UserMapperTests {
    @Autowired
    Admin_UserService admin_userService;
    @Autowired
    Admin_AdminMapper admin_adminMapper;

    //注册管理员账号**************************************
    @Test
    public void register(){
        String name = "root";
        String oldPassword = "root";
        String phone = "110";
        String email = "9999@qq.com";

        List<Admin> list = admin_adminMapper.selectList(new QueryWrapper<Admin>().eq("name", name));
        System.out.println(list);
        if (list.size() != 0){
            System.out.println("用户名被占用");
            return;
        }

        Admin admin = new Admin();
        //对密码进行加密处理: MD5算法  和  随机数+密码+随机数
        //处理方式：md5对密码，连续加密三次
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = MD5Util.getMD5andSalt(oldPassword,salt);
        admin.setPassword(md5Password);
        admin.setSalt(salt);
        //补全数据
        admin.setName(name);
        admin.setCreated_time(new Date());
        admin.setModified_time(new Date());
        admin.setEmail(email);
        admin.setPhone(phone);
        admin.setImg("6666");
        System.out.println(admin);
        admin_adminMapper.insert(admin);
    }


    @Test
    public void getUserList(){
        List<User> userList = admin_userService.getUserList();
        for (User user : userList) {
            System.out.println(user);
        }
    }

}
