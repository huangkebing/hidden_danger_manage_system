package com.hkb.hdms.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 验证码实体类
 *
 * @author huangkebing
 * 2021/03/06
 */
@Getter
@Setter
public class ValidateCode {
    //验证码
    private String code;

    //过期时间
    private LocalDateTime expireTime;

    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusMinutes(expireIn);
    }

    //校验是否验证码已过期
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
