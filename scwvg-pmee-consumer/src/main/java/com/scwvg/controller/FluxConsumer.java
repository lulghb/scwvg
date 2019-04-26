package com.scwvg.controller;

import com.scwvg.entitys.FluxEntity;
import com.scwvg.service.FluxClientService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
