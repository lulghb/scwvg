package com.scwvg.entitys.scwvgponnetwork;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/19 22:20
 * @desc: 
**/
@Data
@NoArgsConstructor
public class WvgLoginLog extends BaseEntity<Long> {
	
    private Long web_log_id;
	
	private String username;
	
	private String loginIp;
	
	private Long status;
	
	private String desc;
	
	private String loginSession;
	
	private Timestamp loginTime;
	
	private String q_beginTime;
	private String q_endTime;
	
}
