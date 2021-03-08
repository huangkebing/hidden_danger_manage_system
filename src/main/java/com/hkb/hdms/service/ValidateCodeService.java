package com.hkb.hdms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hkb.hdms.base.BaseReturnDto;
import com.hkb.hdms.model.pojo.User;

/**
 * @author huangkebing
 * 2021/03/07
 */
public interface ValidateCodeService extends IService<User> {
    BaseReturnDto createCode(String toMail);
}
