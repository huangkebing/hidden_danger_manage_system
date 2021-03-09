package com.hkb.hdms.utils;

import com.hkb.hdms.base.ValidateCodeConstants;
import com.hkb.hdms.model.ValidateCode;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 邮箱验证码生成类
 *
 * @author huangkebing
 * 2021/03/07
 */
@Component
public class ValidateCodeGenerator {

    public ValidateCode generator(String toMail) {
        return new ValidateCode(getCode(), ValidateCodeConstants.EXPIRE_IN, toMail);
    }

    /**
     * 生成指定长度的随机验证码
     */
    private String getCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < ValidateCodeConstants.CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
