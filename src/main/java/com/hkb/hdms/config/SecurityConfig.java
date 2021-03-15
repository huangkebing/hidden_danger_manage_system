package com.hkb.hdms.config;

import com.hkb.hdms.base.Constants;
import com.hkb.hdms.config.auth.EmailAuthenticationProcessingFilter;
import com.hkb.hdms.config.auth.EmailAuthenticationProvider;
import com.hkb.hdms.config.auth.UsernamePasswordAuthenticationProvider;
import com.hkb.hdms.service.UserService;
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
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

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

    private final DataSource dataSource;

    private final UserService userService;

    @Autowired
    public SecurityConfig(EmailAuthenticationProcessingFilter emailAuthenticationProcessingFilter,
                          EmailAuthenticationProvider emailAuthenticationProvider,
                          UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider,
                          DataSource dataSource, UserService userService) {
        this.emailAuthenticationProcessingFilter = emailAuthenticationProcessingFilter;
        this.emailAuthenticationProvider = emailAuthenticationProvider;
        this.usernamePasswordAuthenticationProvider = usernamePasswordAuthenticationProvider;
        this.dataSource = dataSource;
        this.userService = userService;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
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
    public RememberMeServices getRememberMeServices(){
        PersistentTokenBasedRememberMeServices rememberMeServices = new PersistentTokenBasedRememberMeServices("emailCookie", userService, persistentTokenRepository());
        rememberMeServices.setTokenValiditySeconds(24 * 60 * 60);
        rememberMeServices.setParameter("remember");
        return rememberMeServices;
    }

    @Bean
    public AuthenticationFailureHandler getAuthenticationFailureHandler(){
        return new SimpleUrlAuthenticationFailureHandler(Constants.LOGIN_FAILURE_URL_EMAIL);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //添加自定义认证
        auth.authenticationProvider(usernamePasswordAuthenticationProvider);
        auth.authenticationProvider(emailAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        emailAuthenticationProcessingFilter.setAuthenticationFailureHandler(getAuthenticationFailureHandler());
        emailAuthenticationProcessingFilter.setRememberMeServices(getRememberMeServices());

        http.authorizeRequests()
                .antMatchers("/login.html","/login/**").permitAll()
                .antMatchers("/css/**","/images/**","/js/**","/layuimini/**").permitAll()
                .anyRequest().authenticated();

        // 登录配置
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login.html")
                .loginProcessingUrl("/login/password") // 登陆表单提交请求
                .defaultSuccessUrl("/index.html")// 设置默认登录成功后跳转的页面
                .failureUrl(Constants.LOGIN_FAILURE_URL_PASSWORD);



        http.headers().contentTypeOptions().disable();
        http.headers().frameOptions().disable(); // 图片跨域
        http.csrf().disable();//关闭csrf功能:跨站请求伪造,默认只能通过post方式提交logout请求
        http.logout().logoutSuccessUrl("/login.html");

        // 记住我配置
        http.rememberMe()
                .key("passwordCookie")
                .rememberMeParameter("remember")
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(24 * 60 * 60)
                .userDetailsService(userService);

        //添加自定义过滤器
        http.addFilterBefore(emailAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
