package com.scwvg.system.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.scwvg.system.handler.ScwvgAccessDeniedHandler;
import com.scwvg.system.handler.ScwvgAuthenticationEntryPoint;
import com.scwvg.system.service.impl.WvgUserServiceImpl;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 11:52
 * @Description: TODO springSecurity安全配置类，配置权限和角色
 * version 0.1
 */
@EnableWebSecurity //配置注解，启动web认证安全
//@EnableGlobalMethodSecurity(prePostEnabled = true) //启用方法级别的权限认证
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * @param KEY  启用 remember me ，需要不可重复，自定义加盐
	 */
	private static final String KEY="www.scwvg.com";
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	/** 
	 * @Description:  配置客户端，配置由SpringSecurity提供http安全请求
	 * @Author: chen.baihoo
	 * @Date: 2019/4/14 
	 */ 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String [] staticres = new String[]{
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
		// 1. 设置页面要需要权限请求得资源
		http.authorizeRequests()
				// 1.01  设置允许访问的资源路径
				.antMatchers(staticres).permitAll()
				// 1.02  设置允许访问的资源路径必须相相应得权限,可以传数组（临时，部署上线放开）
				.antMatchers("/admins/**").hasAnyAuthority("ROLE_ADMIN")
				// 1.03  设置任何地址的访问均需验证权限
				.anyRequest().authenticated()
				// 1.04  设置基于 from 表单登陆校验
				.and().formLogin()
				// 1.05  设置表单登陆错误，则跳转自定义得提示界面
				.loginPage("/login")
				// 1.06  设置表单登陆成功，则跳转首页
				//.defaultSuccessUrl("/index")
				// 1.07  设置表单登陆错误，则跳转自定义得提示界面
				.failureUrl("/wvglogin/login-error?secError=true")
				// 登录成功处理
				.successHandler(authenticationSuccessHandler)
				// 登录失败处理
				.failureHandler(authenticationFailureHandler)
				// 1.08  设置启用 remember me
				.and().rememberMe().key(KEY)
				// 1.09  设置登陆退出操作
				//.and().logout().logoutSuccessUrl("/logout").permitAll()
				// 1.10  设置类handler方式设置无权限处理异常，拒绝访问重定向到 403 页面
				.and().exceptionHandling().accessDeniedHandler(new ScwvgAccessDeniedHandler())
				// 1.11  设置处理是转发或重定向到登录页面
				.authenticationEntryPoint(new ScwvgAuthenticationEntryPoint());

		// 2 http设置请求头允许来自同一web工程框架内的请求
		http.headers().frameOptions().sameOrigin();

		// 3. 解决中文乱码问题
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		// 3.1 设置字符集 UTF-8
		filter.setEncoding("UTF-8");
		// 3.2 设置覆盖所有请求编码
		filter.setForceEncoding(true);
		http.addFilterBefore(filter,CsrfFilter.class);

		// 4. 回话 session 管理,当用户在其他地方登录了，原来登录过的Session就失效
		http.sessionManagement()
				// 4.1 session 会话超时无效跳转处理
				.invalidSessionStrategy((request,response)->{
					// TODO 待完善 跳转 url
					request.getRequestDispatcher("/session/invalid")
							.forward(request,response);
				})
				// 4.2 设置防止 session 篡改。即认证时，创建一个新http session，原session失效
				.sessionFixation().migrateSession()
				// 4.3 设置同一用户拥有多个并发session
				.maximumSessions(2)
				// 4,4 设置是否阻止新的登录。即当用户在其他地方登录了，原来登录过的Session就失效
				.maxSessionsPreventsLogin(true)
				.and();
	}

	/** 
	 * @Description: 配置身份验证管理构建类
	 * @Author: chen.baihoo
	 * @Date: 2019/4/14 
	 */ 
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//1. 添加用户明细服务层
		auth.userDetailsService(userService());
		//2. 添加身份验证提供类
		auth.authenticationProvider(authenticationProvider());
		// 若要方便与测试那么 就要和 passwordEncoder() 返回得 passwordEncoder() 实现类前呼后应，认证信息存储内存中
		//auth.inMemoryAuthentication().withUser("baihoo").password("12345").roles("ADMIN"); 
	}

	/**
	 * @Description:  配置密码加密编码格式
	 * @Author: chen.baihoo
	 * @Date: 2019/4/14
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		// 使用 BCrypt 加密，注意：原生密码也得采用这个加密类加密
		return new BCryptPasswordEncoder();
		//return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

	/**
	 * @Description: 用戶服务层接口
	 * @Author: chen.baihoo
	 * @Date: 2019/4/14
	 */
	@Bean
	public UserDetailsService userService() {
		//返回用户服务层接口实现
		return new WvgUserServiceImpl();
	}
	/**
	 * 身份验证提供
	 * @return
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		//1. 创建身份验证提供类
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		//2. 设置用戶服務層接口（注：UserService 繼承UserDetailsService，實現其 loadUserByUsername() 方法）
		authenticationProvider.setUserDetailsService(userService());
		//3. 设置密码加密方式
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		//4. 返回身份验证提供类
		return authenticationProvider;
	}
}
