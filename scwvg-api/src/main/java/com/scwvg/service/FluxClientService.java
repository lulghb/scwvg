package com.scwvg.service;

import com.scwvg.entitys.FluxEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date 2019/3/30：16:29
 * 微服务负载接口
 **/
@FeignClient(value = "SCWVG-PMEE-PROVIDER",fallbackFactory = FluxFallbackFactory.class)
public interface FluxClientService {
    @RequestMapping(value = "/flux/provider/list",method = RequestMethod.GET)
    public List<FluxEntity> getaFlux();
}
