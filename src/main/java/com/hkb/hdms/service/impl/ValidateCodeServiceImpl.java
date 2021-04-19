package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.base.Constants;
import com.hkb.hdms.base.R;
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

import javax.servlet.http.HttpSession;

/**
 * @author huangkebing
 * 2021/03/08
 */
@Service
public class ValidateCodeServiceImpl extends ServiceImpl<UserMapper, User> implements ValidateCodeService {

    private final ValidateCodeGenerator codeGenerator;

    private final ValidateCodeMailSender mailSender;

    private final HttpSession session;

    @Autowired
    public ValidateCodeServiceImpl(ValidateCodeGenerator codeGenerator,
                                   ValidateCodeMailSender mailSender,
                                   HttpSession session) {
        this.codeGenerator = codeGenerator;
        this.mailSender = mailSender;
        this.session = session;
    }

    @Override
    public R createCode(String toMail) {
        //校验目标邮箱是否为本系统用户
        User result = this.getOne(new QueryWrapper<User>().eq("email", toMail));
        if (ObjectUtils.isEmpty(result)) {
            return ReturnConstants.EMAIL_NOT_EXIST;
        } else {
            if (result.getLive() == 0) {
                return ReturnConstants.ACCOUNT_FROZEN;
            }
        }

        ValidateCode code = codeGenerator.generator(toMail);

        //验证码存入session中
        session.setAttribute(Constants.VALIDATE_CODE_KEY, code);

        //发送验证码到目标邮箱
        mailSender.sendMail(code.getCode(), toMail);
        return ReturnConstants.SUCCESS;
    }
}
