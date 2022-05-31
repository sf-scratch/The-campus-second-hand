package com.lh.springboot03.service;

import com.lh.springboot03.bean.User;
import com.lh.springboot03.mapper.UserMapper;
import com.lh.springboot03.utils.DateTimeUtil;
import com.lh.springboot03.utils.EmailUtil;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//测试类，不会随项目打包发送
@SpringBootTest
//表示启动这个单元测试类，参数未SpringRunner的实列类型
@RunWith(SpringRunner.class)
public class EmailServiceTests {
    @Autowired
    EmailService emailService;
    @Autowired
    UserMapper userMapper;

    @Test
    public void sendSimpleMail(){
        emailService.sendSimpleMail("我的第二封邮件","我是谁","1763271443@qq.com");
    }

    @Test
    public void sendHtmlMail() throws MessagingException {
        Map<String,String> attachmentMap = new HashMap<>();
        attachmentMap.put("你懂的.png","C:\\Users\\Administrator\\Desktop\\WPS004.png");
        attachmentMap.put("起飞.png","C:\\Users\\Administrator\\Desktop\\WPS001.png");
        emailService.sendHtmlMail("带附件的邮件",
                "欢迎进入<a href=\\\"http://www.baidu.com\\\">百度首页</a>",
                attachmentMap,"1763271443@qq.com");
    }

    @Test
    public void random(){
//        String randomString = UUID.randomUUID().toString();
//        System.out.println(randomString);
//        System.out.println(randomString.substring(0,6));
        System.out.println(EmailUtil.checkEmail("946156@qq.com"));
        System.out.println(EmailUtil.checkEmail("9461555556@55qq.com"));
        System.out.println(EmailUtil.checkEmail("946f156@qq.c5om"));
        System.out.println(EmailUtil.checkEmail("946156@qqcom"));
        System.out.println(EmailUtil.checkEmail("@qq.com"));
        System.out.println(EmailUtil.checkEmail("946156@qq.com."));
        System.out.println(EmailUtil.checkEmail("946.156@qq.com"));
    }
    @Test
    public void date(){
        User user = userMapper.findByUid(16);
        long time = user.getModifiedTime().getTime();
        long time1 = new Date().getTime();
        System.out.println((time1-time)/1000/60);
    }


}
