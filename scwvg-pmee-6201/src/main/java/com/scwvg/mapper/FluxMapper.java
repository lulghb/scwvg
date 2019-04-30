package com.scwvg.mapper;

import com.scwvg.entitys.FluxEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date 2019/3/29：21:10
 **/
@Mapper
public interface FluxMapper {
    public List<FluxEntity> getFlux();
}
