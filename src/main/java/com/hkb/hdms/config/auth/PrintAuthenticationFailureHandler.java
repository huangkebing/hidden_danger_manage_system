package com.hkb.hdms.config.auth;

import com.alibaba.fastjson.JSON;
import com.hkb.hdms.base.BaseReturnDto;
import com.hkb.hdms.base.ReturnConstants;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 认证失败后自定义失败处理器
 *
 * @author huangkebing
 * 2021/03/09
 */
public class PrintAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //认证失败，返回一个JSON数据包，方便前端给出提示信息
        BaseReturnDto result = new BaseReturnDto(ReturnConstants.LOGIN_FAILURE_CODE, e.getMessage());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.append(JSON.toJSONString(result));
        writer.close();
    }
}
