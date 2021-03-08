package com.hkb.hdms.service.impl;

import com.hkb.hdms.base.BaseReturnDto;
import com.hkb.hdms.model.ValidateCode;
import com.hkb.hdms.service.ValidateCodeService;
import com.hkb.hdms.utils.ValidateCodeGenerator;
import com.hkb.hdms.utils.ValidateCodeMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huangkebing
 * 2021/03/08
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    private final ValidateCodeGenerator codeGenerator;

    private final ValidateCodeMailSender mailSender;

    @Autowired
    public ValidateCodeServiceImpl(ValidateCodeGenerator codeGenerator, ValidateCodeMailSender mailSender) {
        this.codeGenerator = codeGenerator;
        this.mailSender = mailSender;
    }

    @Override
    public BaseReturnDto createCode(String toMail) {
        ValidateCode code = codeGenerator.generator();
        //todo 存储验证码
        mailSender.sendMail(code.getCode(),toMail);
        return new BaseReturnDto(0, "success");
    }
}
