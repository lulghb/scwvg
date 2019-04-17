package com.scwvg.controller;

import com.scwvg.entitys.FluxEntity;
import com.scwvg.service.FluxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date 2019/3/29：20:45
 **/
@RestController
public class FluxController {
    @Autowired
    FluxService fluxService;
    @Autowired
    DiscoveryClient client;

    @RequestMapping(value = "/flux/provider/list", method = RequestMethod.GET)
    public List<FluxEntity> list() {
        List<FluxEntity> fluxList = new ArrayList<>();
        fluxList = fluxService.listFlux();
        return fluxList;
    }

    /**
     * 服务发现
     */
    @RequestMapping(value = "/flux/discovery", method = RequestMethod.GET)
    public Object discovery() {
        List<String> sericeList = client.getServices();
        System.out.println("获取到的所有服务：" + sericeList);
        List<ServiceInstance> list = client.getInstances("scwvg-pmee-provider");
        for (ServiceInstance element : list) {
            System.out.println(element.getServiceId() + "\t" + element.getHost() + "\t" + element.getPort() + "\t"
                    + element.getUri());
        }
        return this.client;
    }
}
