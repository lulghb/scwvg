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
        http
                .authorizeRequests()
                //访问：/home 无需登录认证权限
                .antMatchers("/home").permitAll()
                //其他所有资源都需要认证，登陆后访问
                .anyRequest().authenticated()
                //登陆后之后拥有“ADMIN”权限才可以访问/hello方法，否则系统会出现“403”权限不足的提示
                .antMatchers("/admins/**").hasAuthority("ADMIN")
                .and().formLogin()
                //指定登录页是”/login”
                .loginPage("/login")
                .defaultSuccessUrl("/index")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                //退出登录后的默认网址是”/login”
                .logoutSuccessUrl("/login")
                .permitAll()
                .invalidateHttpSession(true)
                .and()
                //登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
                .rememberMe()
                .tokenValiditySeconds(1209600);
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
