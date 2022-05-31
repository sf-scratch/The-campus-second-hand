package com.lh.springboot03.service;

import com.lh.springboot03.bean.User;
import com.lh.springboot03.mapper.UserMapper;
import com.lh.springboot03.utils.TokenUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JedisTests {


    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate<String,User> redisTemplate;

    @Test
    public void jedis01(){
        Jedis jedis = new Jedis("192.168.183.128",6379);
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
            Set<String> k1 = jedis.smembers("k1");
            for (String s : k1) {
                System.out.println(s);
            }
        }
    }

    @Test
    public void jedis02(){
        Jedis jedis = new Jedis("192.168.183.128",6379);
        jedis.hset("users","age","18");
        String hget = jedis.hget("users", "age");
        System.out.println(hget);
    }

    @Test
    public void jedis03(){
        Jedis jedis = new Jedis("192.168.183.128",6379);
        jedis.zadd("china",100d,"shanghai");
        Set<String> china = jedis.zrange("china", 0, -1);
        System.out.println(china);
    }
    @Test
    public void redisTemplate(){
//        System.out.println(redisTemplate.hasKey("kk1"));
//        Boolean expire = redisTemplate.expire("VerifyCode16:count", 1, TimeUnit.MINUTES);
//        redisTemplate.boundValueOps("VerifyCode16:count").set("wdnmd",1, TimeUnit.MINUTES);
        Boolean k1 = redisTemplate.delete("k1");
        System.out.println(k1);
    }

    @Test
    public void redisTemplateObject(){
        User user = userService.selectUserByUid(16);
        System.out.println(user);
        redisTemplate.opsForValue().set("user",user);
        User user1 = redisTemplate.opsForValue().get("userr");
//        Boolean user2 = redisTemplate.hasKey("user");
//        System.out.println(user2);
        System.out.println(user1);
    }

    @Test
    public void updateEmail(){
//        userMapper.updateEmailByUid(3,"6666666666");
        Boolean k1 = redisTemplate.expire("k1", 5, TimeUnit.MINUTES);
    }
}
