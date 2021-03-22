package com.hkb.hdms.utils.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author huangkebing
 * 2021/03/22
 */
@Component
public class RegisterMailSender extends AbstractMailSender{

    @Autowired
    public RegisterMailSender(JavaMailSender sender) {
        super(sender);
    }

    @Override
    public String contextBuild(String[] message) {
        return "【hdms系统】安全隐患管理系统注册成功！您的初始密码为：" + message[0] + "，请登录系统及时修改。" +
                "\n -------------------- \n" +
                "来自hdms安全隐患管理系统";
    }
}
