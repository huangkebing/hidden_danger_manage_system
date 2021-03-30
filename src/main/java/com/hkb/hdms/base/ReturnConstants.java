package com.hkb.hdms.base;

/**
 * BaseReturnDto code message定义
 *
 * @author huangkebing
 * 2021/03/08
 */
public class ReturnConstants {
    public static final R FAILURE = new R(-1, "系统错误，请联系系统管理员");

    public static final R SUCCESS = new R(0, "success");

    public static final R EMAIL_NOT_EXIST = new R(1, "登录账号不存在，请联系系统管理员");

    public static final R PARAMS_EMPTY = new R(2, "必须参数为空，请检查参数");

    public static final R OLD_PASSWORD_ERROR = new R(3, "旧的密码错误，请检查密码");

    public static final R PASSWORD_NOT_SAME = new R(4, "两次密码不一致，请检查密码");

    public static final R EMAIL_EXIST = new R(5, "该邮箱已存在");

    public static final R ACCOUNT_FROZEN = new R(6, "该账号已被冻结，请联系系统管理员");

    public static final R FILE_TYPE_ERROR = new R(7, "文件格式错误，只支持bpmn和zip");
}
