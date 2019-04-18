package com.scwvg.service;

import com.scwvg.entitys.FluxEntity;
import com.scwvg.mapper.FluxMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date 2019/3/29：21:14
 **/
@Service
public class FluxService {
    @Resource
    FluxMapper fluxMapper;

    public List<FluxEntity> listFlux() {
        return fluxMapper.getFlux();
    }
}
