package com.hkb.hdms.utils;

import com.hkb.hdms.base.MailConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author huangkebing
 * 2021/03/22
 */
@Component
public class RegisterMailSender {
    //发送邮件的邮箱
    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender sender;

    @Autowired
    public RegisterMailSender(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendMail(String message, String... to) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //设置邮件主题
        mailMessage.setSubject(MailConstants.REGISTER);
        //设置邮件正文
        mailMessage.setText(contextBuild(message));
        //选择邮件收件人
        mailMessage.setTo(to);
        //设置邮件发件人
        mailMessage.setFrom(from);
        sender.send(mailMessage);
    }

    public String contextBuild(String message) {
        return "【hdms系统】安全隐患管理系统注册成功！您的初始密码为：" + message + "，请登录系统及时修改。" +
                "\n -------------------- \n" +
                "来自hdms安全隐患管理系统";
    }
}
