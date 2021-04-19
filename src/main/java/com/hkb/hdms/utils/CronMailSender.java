package com.hkb.hdms.utils;

import com.hkb.hdms.base.MailConstants;
import com.hkb.hdms.model.pojo.Problem;
import com.hkb.hdms.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author huangkebing
 * 2021/04/16
 */
@Component
public class CronMailSender {
    //发送邮件的邮箱
    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender sender;

    @Autowired
    public CronMailSender(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendMail(List<Problem> problems, User user, String... to) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //设置邮件主题
        mailMessage.setSubject(MailConstants.CRON);
        //设置邮件正文
        mailMessage.setText(contextBuild(problems, user));
        //选择邮件收件人
        mailMessage.setTo(to);
        //设置邮件发件人
        mailMessage.setFrom(from);
        sender.send(mailMessage);
    }

    public String contextBuild(List<Problem> problems, User user) {
        StringBuilder builder = new StringBuilder();
        builder.append(user.getName()).append(" ，早上好！\n");
        builder.append("    您有以下共 ").append(problems.size()).append(" 个隐患待处理，请及时上线处理隐患。\n");
        for (int i = 0; i < problems.size(); i++) {
            Problem problem = problems.get(i);
            builder.append("        ").append(i + 1).append(". ").append(problem.getName()).append("，优先级：");
            switch (problem.getPriority()) {
                case 1: {
                    builder.append("低\n");
                    break;
                }
                case 2: {
                    builder.append("中\n");
                    break;
                }
                case 3: {
                    builder.append("高\n");
                    break;
                }
            }
        }
        builder.append(" -------------------- \n来自hdms安全隐患管理系统");
        return builder.toString();
    }
}
