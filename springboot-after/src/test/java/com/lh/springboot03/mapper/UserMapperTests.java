package com.lh.springboot03.mapper;

import com.lh.springboot03.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

//测试类，不会随项目打包发送
@SpringBootTest
//表示启动这个单元测试类，参数未SpringRunner的实列类型
@RunWith(SpringRunner.class)
public class UserMapperTests {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private RedisTemplate<String,User> redisTemplateUser;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Admin_UserMapper admin_userMapper;
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("tom");
        user.setPassword("ddd");
        Integer insert = userMapper.insert(user);
        System.out.println(insert);
    }
    @Test
    public void findByName(){
        User user = userMapper.findByUsername("彦祖");
        System.out.println(user.getUsername()+"--"+user.getPassword());
    }

    @Test
    public void updateInfo(){
        userMapper.updateInfoByUsername("66","17882237271","1763271443","好好学习",1,new Date());
    }

    @Test
    public void updateInfo2(){
        User user = new User();
        user.setUsername("hello");
        redisTemplateUser.opsForValue().set("a",user,5, TimeUnit.MINUTES);
    }

    @Test
    public void updateInfo3(){
        User user = new User();
        user.setUsername("oo");
        redisTemplateUser.opsForValue().set("a",user,redisTemplateUser.getExpire("a"),TimeUnit.SECONDS);
    }

    @Test
    public void getCurrentMouthRegisterUser(){
        Integer currentMouthRegisterUser = admin_userMapper.getCurrentMouthRegisterUser();
        System.out.println(currentMouthRegisterUser);
    }
}
