package com.scwvg.zgy.system.configuration;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019年5月2日13点03分
 * @Description: TODO 注解式声明事务,使用聲明式事務配置，可以有效規範代碼
 * version 0.1
 */
@EnableTransactionManagement // 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
@Configuration
public class ApplicationContextTransactional{

	//事务方法超时时间设置
	private static final int TX_METHOD_TIMEOUT=10;
	//AOP切面的切点表达式
	private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.scwvg.zgy.*.service.impl.*.*(..))";
	//注入事务管理器
	@Autowired
	private PlatformTransactionManager platformTransactionManager;
	
	/**
	 * 增强(事务)的属性的配置 <br>
	 * 		isolation:DEFAULT	:事务的隔离级别.	<br>
	 * 		propagation				:事务的传播行为.	<br>
	 * 		read-only					:false.不是只读.	<br>
	 * 		timeout						:-1						<br>
	 * 		no-rollback-for			:发生哪些异常不回滚	<br>
	 * 		rollback-for				:发生哪些异常回滚事务	<br>
	 * @return
	 */
	@Bean
	public TransactionInterceptor txAdvice() {
		/**增强(事务)的属性的配置
		 * 	<tx:attributes>
		 * */
		//1. 见名知意，名称匹配事务属性资源
		NameMatchTransactionAttributeSource txAttributeS = new NameMatchTransactionAttributeSource();
		/**propagation="REQUIRED" , timeout=5 ;rollback-for=".. , .."配置*/
		//2. 事务属性规则配置 一
		RuleBasedTransactionAttribute requiredAttr = new RuleBasedTransactionAttribute();
		//2.1 事务属性传播行为特性， PROPAGATION_REQUIRED		:支持当前事务，如果不存在 就新建一个
		requiredAttr.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		//2.2 事务属性超时设置
		requiredAttr.setTimeout(TX_METHOD_TIMEOUT);
		//2.3 事务属性自定义配置发生那些异常回滚
		requiredAttr.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		/**propagation="SUPPORTS" , readOnly="true"配置*/
		//3. 事务属性规则配置 二
		RuleBasedTransactionAttribute supportsAttr = new RuleBasedTransactionAttribute();
		//3.1 事务属性传播行为特性，PROPAGATION_SUPPORTS		:支持当前事务，如果不存在，就不使用事务
		supportsAttr.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
		//3.2 事务属性配置为只读
		supportsAttr.setReadOnly(true);
		/**
		 注意：方法名称来自类匹配的到方法 
		 	【save*, “*”表示匹配任意個字符】
		 	<tx:method .../>
		 */
		//4. 类方法任意模糊匹配，事务属性规则（Map形式存入连接点[Joinpoint]，事务属性规则）
		Map<String , TransactionAttribute>   txMethod = new HashMap<String , TransactionAttribute>();
		txMethod.put("save*", requiredAttr);
		txMethod.put("add*", requiredAttr);
		txMethod.put("insert*", requiredAttr);
		txMethod.put("update*", requiredAttr);
		
		txMethod.put("register*", requiredAttr); //對service層的轉賬業務方法開啓事務增强通知
		
		txMethod.put("delete*", supportsAttr);
		txMethod.put("remove*" , supportsAttr);
		txMethod.put("find*", supportsAttr);
		txMethod.put("select*", supportsAttr);
		txMethod.put("get*", supportsAttr);
		
		//5. 事务属性资源，以设置Map形式设置
		txAttributeS.setNameMap(txMethod);
		//6. 创建最终通知事务拦截器，事务拦截器的构造函数传入所需的构造形参：事务管理器，事务属性资源
		TransactionInterceptor txAdvice = new TransactionInterceptor(platformTransactionManager, txAttributeS);
		//返回要增强通知事务拦截器，
		return txAdvice;
	}
	/**
	 * AOP配置定义切面和切点的信息
	 * @return
	 */
	@Bean
	public Advisor txAdviceAdvisor() {
		//1. 创建切入点，对那些连接点[Joinpoint]进行拦截
		AspectJExpressionPointcut pointcut= new AspectJExpressionPointcut();
		//2. 设置切入点，切点表达式
		pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
		
		//3. 返回定义切面
		return new DefaultPointcutAdvisor(pointcut , txAdvice());
	}
	/**
	 * @Description: 创建事物管理器
	 * @Author: chen.baihoo
	 * @Date: 2019/5/2
	 */
	@Bean(name = "platformTransactionManager")
	public PlatformTransactionManager txManager(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
