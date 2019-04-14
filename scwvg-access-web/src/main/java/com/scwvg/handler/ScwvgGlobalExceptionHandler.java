package com.scwvg.handler;

import com.scwvg.exception.ScwvgException;
import com.scwvg.exception.ScwvgRuntimeException;
import com.scwvg.vo.Response;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 14:21
 * @Description: TODO 工程全局异常处理
 * version 0.1
 * 注意：如果非 ModelAndView 携带返回页面或者 requestDispatcher 调用，都将以 ajax 格式响应到客户端
 */
@ControllerAdvice
@ResponseBody
public class ScwvgGlobalExceptionHandler {
		/**
		 * 加载国际化资源类
		 */
		@Resource
		private MessageSource messageSource;
	
		/**
		 * 声明要捕获的指定异常<br>
		 * 响应给客户端告知内部服务错误<br>
		 * @param request
		 * @param e 自定义的异常
		 * @return
		 */
		@ExceptionHandler(Exception.class)
		@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
		public ResponseEntity<Response> defultExcepitonHandler(HttpServletRequest request, Exception e) {
			if(e instanceof
					ScwvgException) {
				
		        return ResponseEntity.ok().body(new Response(false , e.getMessage()));
			}else if(e instanceof
					ScwvgRuntimeException){

		        return ResponseEntity.ok().body(new Response(false , e.getMessage()));
			}else if(e instanceof 
					/**
					 * 約束違反異常處理器:
					 * 		這對持久化實體 bean 的验证約束違反抛出的異常處理
					 *
					 */
					ConstraintViolationException) {
				
				List<String> msgList = new ArrayList<String>();
				ConstraintViolationException ex = (ConstraintViolationException)e;
				// 消息的参数化和国际化配置
		        Locale locale = LocaleContextHolder.getLocale();
				//獲取批量校驗的異常信息
				ex.getConstraintViolations().forEach(cv -> msgList.add(messageSource.getMessage(getMessage(cv), null, locale)));
				return  ResponseEntity.ok().body(new Response(false , StringUtils.join(msgList, ';')));
			}else if(e instanceof TransactionSystemException){
				List<String> msgList = new ArrayList<String>();
				ConstraintViolationException ex = (ConstraintViolationException)e.getCause().getCause();
				// 消息的参数化和国际化配置
		        Locale locale = LocaleContextHolder.getLocale();
				//獲取批量校驗的異常信息
				ex.getConstraintViolations().forEach(cv -> msgList.add(messageSource.getMessage(getMessage(cv), null, locale)));
				return  ResponseEntity.ok().body(new Response(false , StringUtils.join(msgList, ';')));
			}
			else {
				e.printStackTrace();
				return ResponseEntity.ok().body(new Response(false , e.getMessage()));
			}
		}
		
		private String getMessage(ConstraintViolation<?> cv) {
			String message = cv.getMessage();
			message = message.replace("{", "").replace("}", "");
			return message;
		}
}
