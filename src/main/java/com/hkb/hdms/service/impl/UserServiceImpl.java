package com.hkb.hdms.service.impl;

import com.hkb.hdms.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author huangkebing
 * 2021/03/09
 */
@Component
public class UserServiceImpl implements UserService {


    @Override
    public UserDetails loadUserByEmail(String email) {
        return new User(email,new BCryptPasswordEncoder().encode("123456"), Collections.emptyList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(username,new BCryptPasswordEncoder().encode("123456"), Collections.emptyList());
    }
}
