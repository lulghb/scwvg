package com.scwvg.configuration;

import com.scwvg.handler.ScwvgAccessDeniedHandler;
import com.scwvg.service.impl.WvgUserServiceImpl;
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
import org.springframework.security.web.access.AccessDeniedHandler;

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

	/** 
	 * @Description:  配置客户端，配置由SpringSecurity提供http安全请求
	 * @Author: chen.baihoo
	 * @Date: 2019/4/14 
	 */ 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 1. 设置页面要需要权限请求得资源
		http.authorizeRequests()
				// 1.1  设置允许访问的资源路径
				.antMatchers("/layui/css/**", "/layui/js/**", "/layui/fonts/**", "/index").permitAll()
				// 1.2 设置H2内存数据库允许访问的资源路径
				.antMatchers("/h2-console/**").permitAll()
				// 1.3  设置允许访问的资源路径必须相相应得权限,可以传数组
				.antMatchers("/admins/**").hasAnyAuthority("ROLE_ADMIN")
				// 1.4  设置基于 from 表单登陆校验
				.and().formLogin()
				// 1.5  设置表单登陆错误，则跳转自定义得提示界面
				.loginPage("/login").failureUrl("/wvguser/login-error?secError=true")
				// 1.6  设置启用 remember me
				.and().rememberMe().key(KEY)
				// 1.7   以类handler方式设置无权限处理异常，拒绝访问重定向到 403 页面
				//.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler())
				// 1.8   处理异常，拒绝访问就重定向到 403 页面
				.and().exceptionHandling().accessDeniedPage("/403")
				;
		// 2. 设置H2内存数据库 ，所需参数
		// 2.1 禁用 H2 控制台的 CSRF 防护
		http.csrf().ignoringAntMatchers("/h2-console/**"); 				
		// 2.2 http设置请求头允许来自同一web工程框架内 H2 控制台 的请求
		http.headers().frameOptions().sameOrigin();							
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
	/**
	 * 以类handler方式设置无权限处理器
	 * @return
	 */
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new ScwvgAccessDeniedHandler();
	}
}
