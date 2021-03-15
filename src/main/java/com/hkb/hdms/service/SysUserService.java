package com.hkb.hdms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hkb.hdms.base.BaseReturnDto;
import com.hkb.hdms.model.pojo.User;

/**
 * @author huangkebing
 * 2021/03/15
 */
public interface SysUserService extends IService<User> {
    BaseReturnDto resetPassword(String old, String now, String check);
}
