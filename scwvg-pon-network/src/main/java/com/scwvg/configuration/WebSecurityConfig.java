package com.scwvg.configuration;

import com.scwvg.service.WvgAccessDecisionManager;
import com.scwvg.service.WvgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/4/30
 * @Explain：编写过滤器，页面路径过滤配置
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private WvgAccessDecisionManager wvgAccessDecisionManager;

    @Bean
    UserDetailsService customUserService(){ //注册UsetailsService 的bean
        return new WvgUserService();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()); //user Details Service验证

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] staticres = new String[]{
                "/login",
                "/wvglogin/**",
                "/json/**",
                "/layui/**",
                "/lib/**",
                "/modules/**",
                "/style/**",
                "/tpl/**",
                "/config.js/**",
        };

       http.
               authorizeRequests()
                // 所有用户均可访问的资源
                .antMatchers(staticres).permitAll()
                // ROLE_USER的权限才能访问的资源
                .antMatchers("/admin/**").hasRole("USER")
                // 任何尚未匹配的URL只需要验证用户即可访问
                .anyRequest().authenticated()
                .and()
                .formLogin()
                // 指定登录页面,授予所有用户访问登录页面
                .loginPage("/login")
                //设置默认登录成功跳转页面,错误回到login界面
                .defaultSuccessUrl("/index").failureUrl("/login?error").permitAll()
                .and()
                //开启cookie保存用户数据
                .rememberMe()
                //设置cookie有效期
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                //设置cookie的私钥
                .key("security")
                .and()
                .logout()
                .permitAll();




        // 3. 解决中文乱码问题
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        // 3.1 设置字符集 UTF-8
        filter.setEncoding("UTF-8");

        // 4. 回话 session 管理,当用户在其他地方登录了，原来登录过的Session就失效
        http.sessionManagement()
                // 4.1 session 会话超时无效跳转处理
                .invalidSessionStrategy((request, response) -> {
                    //待完善 跳转 url
                    request.getRequestDispatcher("/session/invalid")
                            .forward(request, response);
                })
                // 4.2 设置防止 session 篡改。即认证时，创建一个新http session，原session失效
                .sessionFixation().migrateSession()
                // 4.3 设置同一用户拥有多个并发session
                .maximumSessions(2)
                // 4,4 设置是否阻止新的登录。即当用户在其他地方登录了，原来登录过的Session就失效
                .maxSessionsPreventsLogin(true)
                .and();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用 BCrypt 加密，注意：原生密码也得采用这个加密类加密
        return new BCryptPasswordEncoder();
        //return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

}
