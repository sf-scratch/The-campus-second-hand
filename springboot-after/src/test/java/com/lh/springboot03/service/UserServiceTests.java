package com.lh.springboot03.service;

import com.lh.springboot03.bean.User;
import com.lh.springboot03.mapper.UserMapper;
import com.lh.springboot03.service.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

//测试类，不会随项目打包发送
@SpringBootTest
//表示启动这个单元测试类，参数未SpringRunner的实列类型
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Test
    public void diddi(){
//        System.out.println(Integer.getInteger("java.runtime"));
        String parent = "file:/java/upload";
        if (parent.split("")[0].equals("file:")){
            parent = parent.substring(5);
        }
        System.out.println(parent);
    }

}
