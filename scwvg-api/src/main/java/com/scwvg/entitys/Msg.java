package com.scwvg.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/4/30
 * @Explain：全局中间信息表，传递中间信息
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Msg {
    private String title;  //消息标题
    private String content;//信息主体
    private String etraInfo;//其他信息
}
