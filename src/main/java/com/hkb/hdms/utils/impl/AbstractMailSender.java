package com.hkb.hdms.utils.impl;

import com.hkb.hdms.utils.MailSender;
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
public abstract class AbstractMailSender implements MailSender {
    //发送邮件的邮箱
    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender sender;

    @Autowired
    public AbstractMailSender(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendMail(String subject, String[] message, String... to) {
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

    public abstract String contextBuild(String[] message);
}
