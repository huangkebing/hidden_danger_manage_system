package com.hkb.hdms.config.auth;

import com.hkb.hdms.base.Constants;
import com.hkb.hdms.model.ValidateCode;
import com.hkb.hdms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Objects;

/**
 * 邮箱验证码登录方式 认证授权
 *
 * @author huangkebing
 * 2021/03/09
 */
@Component
public class EmailAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    private final HttpSession session;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public EmailAuthenticationProvider(UserService userService,
                                       HttpSession session) {
        this.userService = userService;
        this.session = session;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String code = authentication.getCredentials().toString();
        UserDetails userDetails = userService.loadUserByEmail(email);

        if (Objects.isNull(userDetails)) {
            throw new BadCredentialsException("该邮箱不存在");
        }
        ValidateCode validateCode = (ValidateCode) session.getAttribute(Constants.VALIDATE_CODE_KEY);

        //没有验证码情况
        if (validateCode == null) {
            throw new BadCredentialsException("请先发送验证码");
        }

        //登录邮箱和发送验证码邮箱不同
        if (!userDetails.getUsername().equals(validateCode.getToMail())) {
            throw new BadCredentialsException("请先发送验证码");
        }

        //验证码过期
        if (validateCode.isExpired()) {
            throw new BadCredentialsException("验证码已过期，请重新发送");
        }

        //验证码错误
        if (!passwordEncoder.matches(code, passwordEncoder.encode(validateCode.getCode()))) {
            throw new BadCredentialsException("验证码错误");
        }

        //校验完成后，移除session中的值
        session.removeAttribute(Constants.VALIDATE_CODE_KEY);
        EmailAuthenticationToken token = new EmailAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
        token.setDetails(authentication.getDetails());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
