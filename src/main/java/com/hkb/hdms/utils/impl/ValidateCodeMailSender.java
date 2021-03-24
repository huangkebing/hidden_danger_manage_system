package com.hkb.hdms.utils.impl;

import com.hkb.hdms.base.ValidateCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author huangkebing
 * 2021/03/06
 */
@Component
public class ValidateCodeMailSender extends AbstractMailSender {

    @Autowired
    public ValidateCodeMailSender(JavaMailSender sender) {
        super(sender);
    }

    /**
     * 根据message生成邮件正文
     *
     * @param message 邮件动态的信息，本类中为登录的验证码
     */
    @Override
    public String contextBuild(String[] message) {
        return "【hdms系统】登录验证码：" + message[0] + "，" +
                ValidateCodeConstants.EXPIRE_IN +
                "分钟内有效。" +
                "\n -------------------- \n" +
                "来自hdms安全隐患管理系统，请勿泄漏验证码！";
    }
}
