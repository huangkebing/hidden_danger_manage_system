package com.hkb.hdms.service;

import com.hkb.hdms.base.BaseReturnDto;

/**
 * @author huangkebing
 * 2021/03/07
 */
public interface ValidateCodeService {
    BaseReturnDto createCode(String toMail);
}
