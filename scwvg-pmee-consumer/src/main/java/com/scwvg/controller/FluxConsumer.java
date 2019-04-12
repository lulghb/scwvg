package com.scwvg.controller;

import com.scwvg.entitys.FluxEntity;
import com.scwvg.service.FluxClientService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date 2019/3/27：18:30
 **/
@RestController
public class FluxConsumer {
    @Resource
    FluxClientService fcs;

    @RequestMapping(value = "/consumer/list")
    public List<FluxEntity> list() {
        return fcs.getaFlux();
    }

    @RequestMapping(value = "/userLogin")
    @ResponseBody
    public Map userLogin(HttpServletRequest request){
        Map res= new HashMap();
        String userName=request.getParameter("username");
        String password=request.getParameter("password");
        String vercode =request.getParameter("vercode");
        String remember =request.getParameter("remember");
        System.out.println("获取到的用户名是："+userName+"密码是："+password+"验证码是："+vercode);

        res.put("code","0");
        res.put("msg","登录成功");
        res.put("data","access_token:c262e61cd13");
        return res;
    }
    /**
     * 服务发现
     *
     * @return
     */
    /*@RequestMapping(value = "consumer/scwvg/disconvery")
    public Object discovery() {
        return restTemplate.getForObject(REST_URL_PREFIX + "/flux/discovery", Object.class);
    }*/
}
