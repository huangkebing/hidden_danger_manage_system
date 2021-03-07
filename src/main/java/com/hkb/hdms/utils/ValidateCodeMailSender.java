package com.hkb.hdms.utils;

import com.hkb.hdms.base.ValidateCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author huangkebing
 * 2021/03/06
 */
@Component
public class ValidateCodeMailSender implements MailSender {

    //发送邮件的邮箱
    @Value("${spring.mail.username}")
    private String from;

    //邮件主题
    private static final String SUBJECT = "hdms系统登录验证码";

    private final JavaMailSender sender;

    @Autowired
    public ValidateCodeMailSender(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendMail(String message, String... to) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //设置邮件主题
        mailMessage.setSubject(SUBJECT);
        //设置邮件正文
        mailMessage.setText(contextBuild(message));
        //选择邮件收件人
        mailMessage.setTo(to);
        //设置邮件发件人
        mailMessage.setFrom(from);
        sender.send(mailMessage);
    }

    /**
     * 根据message生成邮件正文
     * @param message 邮件动态的信息，本类中为登录的验证码
     */
    private String contextBuild(String message) {
        return "【hdms系统】登录验证码：" + message + "，" +
                ValidateCodeConstants.EXPIRE_IN +
                "分钟内有效。" +
                "\n -------------------- \n" +
                "来自hdms安全隐患管理系统，请勿泄漏验证码！";
    }
}
