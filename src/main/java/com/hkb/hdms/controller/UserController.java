package com.hkb.hdms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 处理用户信息相关接口
 *
 * @author huangkebing
 * 2021/03/15
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/password.html")
    public Object userPage(){
        return "user/password";
    }
}
