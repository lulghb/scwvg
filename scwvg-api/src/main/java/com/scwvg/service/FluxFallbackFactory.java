package com.scwvg.service;

import com.scwvg.entitys.FluxEntity;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date 2019/3/30：20:10
 */

@Component
public class FluxFallbackFactory implements FallbackFactory<FluxClientService> {

    @Override
    public FluxClientService create(Throwable throwable) {
        return new FluxClientService() {
            @Override
            public List<FluxEntity> getaFlux() {
                List<FluxEntity> feList = new ArrayList<FluxEntity>();
                FluxEntity fe = new FluxEntity();
                fe.setOlt_ip("系统故障，请联系管理员！");
                feList.add(fe);
                return feList;
            }
        };
    }
}
