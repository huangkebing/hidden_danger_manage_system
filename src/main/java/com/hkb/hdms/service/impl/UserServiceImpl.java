package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.base.Constants;
import com.hkb.hdms.mapper.TypeMapper;
import com.hkb.hdms.mapper.UserMapper;
import com.hkb.hdms.model.pojo.User;
import com.hkb.hdms.model.pojo.UserRole;
import com.hkb.hdms.model.vo.TypeVo;
import com.hkb.hdms.service.RoleService;
import com.hkb.hdms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huangkebing
 * 2021/03/09
 */
@Component
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final RoleService roleService;

    private final HttpSession session;

    private final TypeMapper typeMapper;

    @Autowired
    public UserServiceImpl(RoleService roleService, HttpSession session, TypeMapper typeMapper) {
        this.roleService = roleService;
        this.session = session;
        this.typeMapper = typeMapper;
    }

    @Override
    public UserDetails loadUserByEmail(String email) {
        email += Constants.EMAIL_SUFFIX;
        User queryUser = this.getOne(new QueryWrapper<User>().eq("email", email));
        session.setAttribute(Constants.LOGIN_USER_KEY, queryUser);
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
        if(!username.contains(Constants.EMAIL_SUFFIX)){
            username += Constants.EMAIL_SUFFIX;
        }
        User queryUser = this.getOne(new QueryWrapper<User>().eq("email", username));
        session.setAttribute(Constants.LOGIN_USER_KEY, queryUser);
        UserDetails userDetails = null;
        if (queryUser != null) {
            String password = queryUser.getPassword();
            //获取权限
            Collection<GrantedAuthority> authorities = getAuthorities(queryUser);
            //账号是否冻结
            boolean live = queryUser.getLive() == 1;
            userDetails = new org.springframework.security.core.userdetails.User(
                    username, password, true, true, true, live, authorities);
        }
        return userDetails;
    }

    //获取用户角色
    private Collection<GrantedAuthority> getAuthorities(User user) {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        UserRole role = roleService.getById(user.getRole());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

        if(role.getQuestion() == 0){
            authorities.add(new SimpleGrantedAuthority("GROUP_" + "ALL"));

        }
        else{
            List<TypeVo> typeVos = typeMapper.selectTypesWithUser(user.getId());
            List<String> groups = typeVos.stream().map(TypeVo::getName).collect(Collectors.toList());
            for (String group : groups) {
                authorities.add(new SimpleGrantedAuthority("GROUP_" + group));
            }
        }
        return authorities;
    }
}
