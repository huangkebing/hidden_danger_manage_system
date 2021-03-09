package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.mapper.UserMapper;
import com.hkb.hdms.model.pojo.User;
import com.hkb.hdms.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public UserDetails loadUserByEmail(String email) {
        User queryUser = this.getOne(new QueryWrapper<User>().eq("email", email));
        UserDetails userDetails = null;
        if(queryUser != null){
            userDetails=new org.springframework.security.core.userdetails.User(email, new BCryptPasswordEncoder().encode("123456"), Collections.emptyList());
        }
        return userDetails;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User queryUser = this.getOne(new QueryWrapper<User>().eq("email", username));
        UserDetails userDetails = null;
        if(queryUser != null){
            String password = queryUser.getPassword();
            userDetails=new org.springframework.security.core.userdetails.User(username, new BCryptPasswordEncoder().encode(password), Collections.emptyList());
        }
        return userDetails;
    }
}
