package com.hkb.hdms.controller;

import com.hkb.hdms.base.Constants;
import com.hkb.hdms.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录相关Controller
 * @author huangkebing
 * 2021/02/11
 */
@Controller
public class LoginController {

    private final ValidateCodeService validateCodeService;

    @Autowired
    public LoginController(ValidateCodeService validateCodeService) {
        this.validateCodeService = validateCodeService;
    }

    @GetMapping("/login.html")
    public String toLogin(){
        return "login";
    }

    @GetMapping("/index.html")
    public String toIndex(){
        return "index";
    }

    @GetMapping("/login/validateCode/{toMail}")
    @ResponseBody
    public Object validateCode(@PathVariable String toMail){
        return validateCodeService.createCode(toMail + Constants.EMAIL_SUFFIX);
    }

    @GetMapping("/hello")
    @ResponseBody
    public Object hello(){
        return "hello";
    }
}
