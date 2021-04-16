package com.hkb.hdms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author huangkebing
 * 2021/04/16
 */
@Component
public class NoticeMailSender {
    //发送邮件的邮箱
    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender sender;

    @Autowired
    public NoticeMailSender(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendMail(String subject, String message, String... to) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //设置邮件主题
        mailMessage.setSubject(subject);
        //设置邮件正文
        mailMessage.setText(contextBuild(message));
        //选择邮件收件人
        mailMessage.setTo(to);
        //设置邮件发件人
        mailMessage.setFrom(from);
        sender.send(mailMessage);
    }

    public String contextBuild(String message) {
        return "【hdms系统】您处理过的安全隐患有如下变动：\n" +
                "    " + message +
                "\n -------------------- \n来自hdms安全隐患管理系统";
    }
}
