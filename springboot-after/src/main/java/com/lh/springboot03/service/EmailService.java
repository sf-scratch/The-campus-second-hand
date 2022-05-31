package com.lh.springboot03.service;

import javax.mail.MessagingException;
import java.util.Map;

public interface EmailService {
    //    发送简单邮件
    void sendSimpleMail(String subject, String text, String to);

    //    发送带有链接和附件的复杂邮件
    void sendHtmlMail(String subject, String text, Map<String, String> attachmentMap, String to) throws MessagingException;

//    给邮箱发送验证码，并将验证码存入session
//    void sendVerificationCodeByEmail(String email, HttpSession session);

    //    给邮箱发送验证码，并将验证码对应的redis的key存入cookie
    Long sendVerificationCodeByEmail(String email, String verifyCodeName);

//    验证码是否存在，若存在返回其值
    String getVerifyCodeValue(String verifyCode);

//    暂时保存newEmail
    void saveTempNewEmail(Integer uid,String newEmail);


}
