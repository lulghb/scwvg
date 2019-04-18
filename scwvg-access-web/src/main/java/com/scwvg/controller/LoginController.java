package com.scwvg.controller;

import com.scwvg.vo.Response;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @project: 黑龙江电信接入适配系统
 * @author: chen.baihoo
 * @date: 2019/4/14 16:44
 * @Description: TODO 登陆控制层
 * version 0.1
 */
@RestController
@RequestMapping("/wvglogin")
@Slf4j
public class LoginController {

    private static String prefix="scwvg-zuul.com/wvg";

    /**
     * @Description: 图片验证码
     * @Author: chen.baihoo
     * @Date: 2019/4/14
     */
    @RequestMapping("/images/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }
    /**
     * @Description: 图片验证码 匹配
     * @Author: chen.baihoo
     * @Date: 2019/4/14
     */
    @GetMapping("/vercode")
    public ResponseEntity<Response> vercode(String vercode, HttpServletRequest request){
        if(!CaptchaUtil.ver(vercode, request))
            return ResponseEntity.ok().body(new Response(false ,"图形验证码输入有误！"));
        return ResponseEntity.ok().body(new Response(true ,"处理成功"));
    }

    /**
     * @Description:  登陆错误，返回登陆页面，并添加错误信息
     * @Author: chen.baihoo
     * @Date: 2019/4/14
     */
    @GetMapping("/login-error")
    public ResponseEntity<Response> loginError(HttpSession session ,@RequestParam(value = "secError", required = true) Boolean secError) {

        //1. 获取登陸用戶抛出的異常信息
        Exception exception = (Exception)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        //2. 判断异常信息是否是“Bad credentials”损坏的证书，即登陆错误
        if("Bad credentials".equals(exception.getMessage())) {
            return ResponseEntity.ok().body(new Response(false ,"登陆失败，账号或密码错误~！"));
        }else {
            return ResponseEntity.ok().body(new Response(false ,exception.getMessage()));
        }
    }
}
