package com.hkb.hdms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录相关Controller
 * @author huangkebing
 * 2021/02/11
 */
@Controller("/login")
public class LoginController {

    @GetMapping("/validateCode/{toMail}")
    @ResponseBody
    public String validateCode(@PathVariable String toMail){
        return "hello";
    }
}
