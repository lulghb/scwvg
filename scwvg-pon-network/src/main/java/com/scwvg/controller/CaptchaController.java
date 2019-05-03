package com.scwvg.controller;

import com.scwvg.entitys.RequestMassge;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/3
 * @Explain：验证码处理
 **/
@RestController
@RequestMapping("/capctha")
public class CaptchaController {
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
    public ResponseEntity<RequestMassge> vercode(String vercode, HttpServletRequest request){
        if(!CaptchaUtil.ver(vercode, request))
            return ResponseEntity.ok().body(new RequestMassge(false ,"图形验证码输入有误！"));
        return ResponseEntity.ok().body(new RequestMassge(true ,"处理成功"));
    }
}
