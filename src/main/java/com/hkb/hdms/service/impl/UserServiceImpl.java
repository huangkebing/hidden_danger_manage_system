package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.base.Constants;
import com.hkb.hdms.mapper.UserMapper;
import com.hkb.hdms.model.pojo.User;
import com.hkb.hdms.model.pojo.UserRole;
import com.hkb.hdms.service.RoleService;
import com.hkb.hdms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author huangkebing
 * 2021/03/09
 */
@Component
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByEmail(String email) {
        email += Constants.EMAIL_SUFFIX;
        User queryUser = this.getOne(new QueryWrapper<User>().eq("email", email));
        UserDetails userDetails = null;
        if (queryUser != null) {
            Collection<GrantedAuthority> authorities = getAuthorities(queryUser);
            userDetails = new org.springframework.security.core.userdetails.User(email,
                    new BCryptPasswordEncoder().encode("123456"), authorities);
        }
        return userDetails;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        username += Constants.EMAIL_SUFFIX;
        User queryUser = this.getOne(new QueryWrapper<User>().eq("email", username));
        UserDetails userDetails = null;
        if (queryUser != null) {
            String password = queryUser.getPassword();
            //获取权限
            Collection<GrantedAuthority> authorities = getAuthorities(queryUser);
            userDetails = new org.springframework.security.core.userdetails.User(username,
                    new BCryptPasswordEncoder().encode(password), authorities);
        }
        return userDetails;
    }

    //获取用户角色
    private Collection<GrantedAuthority> getAuthorities(User user) {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        UserRole role = roleService.getById(user.getRole());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        return authorities;
    }
}
