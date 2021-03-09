package com.hkb.hdms.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author huangkebing
 * 2021/03/09
 */
public interface UserService extends UserDetailsService {
    UserDetails loadUserByEmail(String email);
}
