package com.lh.springboot03.service.impl;

import com.lh.springboot03.config.MailProperties;
import com.lh.springboot03.mapper.UserMapper;
import com.lh.springboot03.service.EmailService;
import com.lh.springboot03.service.exception.EmailException;
import com.lh.springboot03.service.exception.VerifyCodeNotFindException;
import com.lh.springboot03.utils.EmailUtil;
import com.lh.springboot03.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class EmailServiceImpl implements EmailService {
//    邮件配置
    @Autowired
    MailProperties mailProperties;
//    发送邮件的实体类
    @Autowired
    JavaMailSenderImpl javaMailSender;
    @Autowired
    UserMapper userMapper;
//    操作redis的实体类
    @Autowired
    private RedisTemplate<String,String> redisTemplate;



//    验证码有效时间(min)
    private static final int EffectiveTime = 2;

    //    发送简单邮件
    @Override
    public void sendSimpleMail(String subject, String text, String to) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailProperties.getFrom());
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }

    //    发送邮件是富文本（附件，图片，html等）
    @Override
    public void sendHtmlMail(String subject, String text, Map<String, String> attachmentMap, String to) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //是否发送的邮件是富文本（附件，图片，html等）
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(mailProperties.getFrom());
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("富文本");
        mimeMessageHelper.setText(text, true);//重点，默认为false，显示原始html代码，无效果
        if (attachmentMap != null) {
            for (String key : attachmentMap.keySet()) {
                String value = attachmentMap.get(key);
                File file = new File(value);
                if (file.exists()) {
                    mimeMessageHelper.addAttachment(key, new FileSystemResource(file));
                }
            }
        }
        javaMailSender.send(mimeMessage);
    }



    @Override
    public Long sendVerificationCodeByEmail(String email,String verifyCodeName) {
        String verificationCode = UUID.randomUUID().toString().substring(0, 6);
        System.out.println(verificationCode);
        if (!EmailUtil.checkEmail(email)) {
            System.out.println("邮箱格式错误");
            throw new EmailException("邮箱格式错误");
        }
        try {
//            给用户发送验证信息
            sendSimpleMail("验证信息", verificationCode, email);
//            将该验证信息存入redis，并为其设置有效时间
            redisTemplate.boundValueOps(verifyCodeName).set(verificationCode,EffectiveTime, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EmailException("邮箱信息错误");
        }
        return (long)(EffectiveTime * 60);
    }



    @Override
    public String getVerifyCodeValue(String verifyCode) {
        String curVerifyCode = redisTemplate.opsForValue().get(verifyCode);
        if (curVerifyCode == null){
            throw new VerifyCodeNotFindException("请获取验证码");
        }
        return curVerifyCode;
    }

    @Override
    public void saveTempNewEmail(Integer uid,String newEmail){
        redisTemplate.opsForValue().set(TokenUtil.getTokenNewEmail(uid),newEmail,EffectiveTime,TimeUnit.MINUTES);
    }


}
