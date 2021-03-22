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
 * 账号密码校验逻辑
 *
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
        // 获取邮箱和密码
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        //获取userDetails
        UserDetails userDetails = userService.loadUserByUsername(username);
        //校验
        if (Objects.isNull(userDetails)){
            throw new BadCredentialsException("该邮箱不存在");
        }
        if(!userDetails.isAccountNonLocked()){
            throw new BadCredentialsException("该账号已被冻结，请联系系统管理员");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("密码错误");
        }
        //返回经过认证的Authentication
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                username, password, userDetails.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
