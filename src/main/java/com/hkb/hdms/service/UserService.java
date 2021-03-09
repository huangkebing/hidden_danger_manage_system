package com.hkb.hdms.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.hkb.hdms.model.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author huangkebing
 * 2021/03/09
 */
public interface UserService extends UserDetailsService, IService<User> {
    UserDetails loadUserByEmail(String email);
}
