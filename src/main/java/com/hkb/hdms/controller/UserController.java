package com.hkb.hdms.controller;

import com.hkb.hdms.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理用户信息相关接口
 *
 * @author huangkebing
 * 2021/03/15
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final SysUserService sysUserService;

    @Autowired
    public UserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 修改密码页面跳转
     */
    @RequestMapping("/password.html")
    public Object userPasswordPage() {
        return "user/password";
    }

    @RequestMapping("/user.html")
    public Object userPage(){
        return "user/user";
    }

    /**
     * 修改密码接口
     * @param old 旧密码
     * @param now 新密码
     * @param check 确认密码
     * */
    @PostMapping("/resetPassword")
    @ResponseBody
    public Object resetPassword(String old, String now,String check){
        return sysUserService.resetPassword(old, now, check);
    }
}
