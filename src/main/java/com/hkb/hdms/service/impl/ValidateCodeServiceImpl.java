package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.base.BaseReturnDto;
import com.hkb.hdms.base.ReturnConstants;
import com.hkb.hdms.mapper.UserMapper;
import com.hkb.hdms.model.ValidateCode;
import com.hkb.hdms.model.pojo.User;
import com.hkb.hdms.service.ValidateCodeService;
import com.hkb.hdms.utils.ValidateCodeGenerator;
import com.hkb.hdms.utils.ValidateCodeMailSender;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huangkebing
 * 2021/03/08
 */
@Service
public class ValidateCodeServiceImpl extends ServiceImpl<UserMapper, User> implements ValidateCodeService {

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
        User result = this.getOne(new QueryWrapper<User>().eq("email", toMail));
        if(ObjectUtils.isEmpty(result)){
            return ReturnConstants.EMAIL_NOT_EXIST;
        }
        //todo 存储验证码
        mailSender.sendMail(code.getCode(),toMail);
        return ReturnConstants.SUCCESS;
    }
}
