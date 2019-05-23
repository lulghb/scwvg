package com.scwvg.entitys;

import lombok.Data;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/23
 * @Explain：首页今日告警查询
 **/
@Data
public class AlarmCounts extends Console{
    private Long alarm_mubers;//告警产生量
    private Long alarm_clear;//清除量
    private Long affect_muber;//影响用户数

}
