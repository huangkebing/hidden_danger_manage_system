package com.hkb.hdms.utils;

import java.util.UUID;

/**
 * 生成12位随机密码
 *
 * @author huangkebing
 * 2021/03/22
 */
public class PasswordUtil {
    public static String getPassword(){
        return UUID.randomUUID().toString().replaceAll("-","").substring(6,18);
    }
}
