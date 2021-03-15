package com.hkb.hdms.base;

/**
 * BaseReturnDto code message定义
 *
 * @author huangkebing
 * 2021/03/08
 */
public class ReturnConstants {
    public static final BaseReturnDto FAILURE = new BaseReturnDto(-1, "系统错误，请联系系统管理员");

    public static final BaseReturnDto SUCCESS = new BaseReturnDto(0, "success");

    public static final BaseReturnDto EMAIL_NOT_EXIST = new BaseReturnDto(1, "登录账号不存在，请联系系统管理员");

    public static final BaseReturnDto PASSWORD_EMPTY = new BaseReturnDto(2, "密码为空，请输入必须项");

    public static final BaseReturnDto OLD_PASSWORD_ERROR = new BaseReturnDto(3, "旧的密码错误，请检查密码");

    public static final BaseReturnDto PASSWORD_NOT_SAME = new BaseReturnDto(4, "两次密码不一致，请检查密码");
}
