package com.hkb.hdms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hkb.hdms.base.R;
import com.hkb.hdms.model.pojo.User;

import java.util.Map;

/**
 * @author huangkebing
 * 2021/03/15
 */
public interface SysUserService extends IService<User> {
    R resetPassword(String old, String now, String check);

    Map<String, Object> getUser(User user, int limit, int page);

    R addUser(User user);

    R updateUser(User user);

    R deleteUser(Long id);
}
