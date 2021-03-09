package com.hkb.hdms.config;

import com.hkb.hdms.config.auth.EmailAuthenticationProcessingFilter;
import com.hkb.hdms.config.auth.EmailAuthenticationProvider;
import com.hkb.hdms.config.auth.PrintAuthenticationFailureHandler;
import com.hkb.hdms.config.auth.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author huangkebing
 * 2021/03/08
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final EmailAuthenticationProcessingFilter emailAuthenticationProcessingFilter;

    private final EmailAuthenticationProvider emailAuthenticationProvider;

    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Autowired
    public SecurityConfig(EmailAuthenticationProcessingFilter emailAuthenticationProcessingFilter,
                          EmailAuthenticationProvider emailAuthenticationProvider,
                          UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider) {
        this.emailAuthenticationProcessingFilter = emailAuthenticationProcessingFilter;
        this.emailAuthenticationProvider = emailAuthenticationProvider;
        this.usernamePasswordAuthenticationProvider = usernamePasswordAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public AuthenticationFailureHandler printAuthenticationFailureHandler() {
        return new PrintAuthenticationFailureHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //添加自定义认证
        auth.authenticationProvider(usernamePasswordAuthenticationProvider);
        auth.authenticationProvider(emailAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        emailAuthenticationProcessingFilter.setAuthenticationFailureHandler(printAuthenticationFailureHandler());
        http.authorizeRequests()
                .antMatchers("/","/index").permitAll()
                .antMatchers("/login","/login.html").permitAll()
                .antMatchers("/*").authenticated();

        // 登录配置
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login.html")
                .loginProcessingUrl("/login/password") // 登陆表单提交请求
                .defaultSuccessUrl("/index")// 设置默认登录成功后跳转的页面
                .failureHandler(printAuthenticationFailureHandler());



        http.headers().contentTypeOptions().disable();
        http.headers().frameOptions().disable(); // 图片跨域
        http.csrf().disable();//关闭csrf功能:跨站请求伪造,默认只能通过post方式提交logout请求
        http.logout().logoutSuccessUrl("/");

        // 记住我配置
        http.rememberMe().rememberMeParameter("remember");

        //添加自定义过滤器
        http.addFilterBefore(emailAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
