package com.hkb.hdms.base;

/**
 * BaseReturnDto code message定义
 *
 * @author huangkebing
 * 2021/03/08
 */
public class ReturnConstants {
    public static final BaseReturnDto SUCCESS = new BaseReturnDto(0, "success");

    public static final BaseReturnDto EMAIL_NOT_EXIST = new BaseReturnDto(1, "登录账号不存在，请联系系统管理员");

    public static final int LOGIN_FAILURE_CODE = 2;
}
