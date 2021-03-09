package com.hkb.hdms.config.auth;

import com.hkb.hdms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;

/**
 * @author huangkebing
 * 2021/03/09
 */
@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UsernamePasswordAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取电话号码和验证码
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        //1、去调用自己实现的UserDetailsService，返回UserDetails
        UserDetails userDetails = userService.loadUserByUsername(username);
        //2、对 UserDetails 的信息进行校验，主要是帐号是否被冻结，是否过期等
        //3、密码进行检查，这里调用了PasswordEncoder，检查 UserDetails 是否可用。
        if (Objects.isNull(userDetails) || !passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("账号或密码错误");
        }
        //4、返回经过认证的Authentication
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                password, null, Collections.emptyList());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
