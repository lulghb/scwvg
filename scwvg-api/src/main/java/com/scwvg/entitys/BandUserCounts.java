package com.scwvg.entitys;

import lombok.Data;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/23
 * @Explain：首页用户增减情况
 **/
@Data
public class BandUserCounts extends Console{
    private Long userSum;
    private Long addSum;
    private Long delSum;
}
