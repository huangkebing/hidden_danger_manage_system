package com.hkb.hdms.config.auth;

import com.alibaba.druid.util.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huangkebing
 * 2021/03/09
 */
@Component
public class EmailAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public EmailAuthenticationProcessingFilter(@Lazy AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/login/email", "POST"));
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("该接口不支持" + request.getMethod());
        }
        String email = obtainEmail(request);
        String code = obtainCode(request);
        if (StringUtils.isEmpty(email)) email = "";
        if (StringUtils.isEmpty(code)) code = "";
        email = email.trim();
        code = code.trim();
        EmailAuthenticationToken token = new EmailAuthenticationToken(email, code);
        setDetails(request, token);
        return this.getAuthenticationManager().authenticate(token);
    }

    private String obtainEmail(HttpServletRequest request) {
        String emailParameter = "toMail";
        return request.getParameter(emailParameter);
    }

    private String obtainCode(HttpServletRequest request) {
        String codeParameter = "code";
        return request.getParameter(codeParameter);
    }

    private void setDetails(HttpServletRequest request,
                            AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
