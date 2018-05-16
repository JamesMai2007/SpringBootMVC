package com.example.sbmvc.config;

import com.example.sbmvc.filter.ValidateCodeSecurityFilter;
import com.example.sbmvc.service.UserService;
import com.example.sbmvc.validatecode.Captcha;
import com.example.sbmvc.util.SecurityFailureHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SpringSecurityConfig.class);

    @Override
    public void configure(WebSecurity web) throws Exception {
        //不过滤下静态文件
        web.ignoring().mvcMatchers("/js/**" , "/**/*.css" , "/favicon.ico" , "/error" , "/captcha.jpg");
    }

    private SecurityFailureHandler securityFailureHandler = new SecurityFailureHandler();

    @Override
    public void configure(HttpSecurity http) throws Exception {

        securityFailureHandler.setDefaultFailureUrl("/login.html?error=true");

        ValidateCodeSecurityFilter captchaFilter = new ValidateCodeSecurityFilter(captcha,"/login");
        captchaFilter.setSecurityFailureHandler(securityFailureHandler);

        http.addFilterBefore(captchaFilter , UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/public.html" , "/login.html").permitAll()        //不需要登陆 , 这里也需要加上login.html，否则在错误跳转时会报Access is denied错误
                    //.antMatchers("/" , "/index.html" , "/test.html").rememberMe() //这里加上reamberMe()后就固定死用这种方式访问，所以不可取  //记住我功能，登陆过就可以访问
                    .antMatchers("/auth.html").fullyAuthenticated()     //需要完整登陆，不能使用记住我功能访问
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login.html")
                    .loginProcessingUrl("/login")       //修改登陆处理请求链接名
                    .failureUrl("/login.html?error=true")
                    .failureHandler(securityFailureHandler)
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
                .and()
                .logout()
                    .permitAll()
                .and()
                .rememberMe()
                    .tokenRepository(persistentTokenRepository) //把token保存到数据库，让spring security管理
                    .userDetailsService(userService)
                    .key("sbmvc")                           //key值 当作生成token的盐值
                    .rememberMeCookieName("reme-cookie")
                    .rememberMeParameter("remember-me")
                    .tokenValiditySeconds(180)              //token失效时间，秒
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/403.html")
        ;

    }

    @Autowired
    Captcha captcha;

    @Autowired
    UserService userService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PersistentTokenRepository persistentTokenRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Bean  //必须返回AuthenticationProvider类型，否则会有两个不同类型的bean出现
    public AuthenticationProvider authenticationProvider() {
        //权限提供者，用于配置UserDetailService，使其可以用数据库查询登录用户

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);       //密码编码器
        authenticationProvider.setMessageSource(messageSource);
        return authenticationProvider;
    }

    /**
     * 配置自定义的UserDetailsService和AuthenticationProvider
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userService);
        auth.authenticationProvider(authenticationProvider);
    }

    /**
     * 创建用户密码加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 创建使用jdbc来持久化memberme token到数据库，让spring security管理
     * @return
     */
    @Bean
    public PersistentTokenRepository jdbcTokenRepository(){
        //使用jdbc保存token，并创建持久登陆表

        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setJdbcTemplate(jdbcTemplate);

        //只运行一次后注释掉，用于第一次创建数据表，自动创建登陆数据表，用于保存
        //jdbcTokenRepository.setCreateTableOnStartup(true);

        return jdbcTokenRepository;
    }


}


