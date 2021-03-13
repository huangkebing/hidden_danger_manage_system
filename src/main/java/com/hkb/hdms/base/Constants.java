package com.hkb.hdms.base;

/**
 * 系统常量
 *
 * @author huangkebing
 * 2021/03/08
 */
public class Constants {
    //验证码存入session 使用的key
    public static final String VALIDATE_CODE_KEY = "VALIDATE_CODE_KEY";

    //认证失败跳转页面
    public static final String LOGIN_FAILURE_URL_EMAIL = "/login.html?error1";
    public static final String LOGIN_FAILURE_URL_PASSWORD = "/login.html?error2";

    //邮箱后缀
    public static final String EMAIL_SUFFIX = "@qq.com";

    public static final String LOGIN_USER_KEY = "loginUser";
}
