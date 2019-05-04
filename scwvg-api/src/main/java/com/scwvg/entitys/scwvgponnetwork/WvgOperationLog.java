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
public class WvgOperationLog extends BaseEntity<Long> {
	
    private Long id;
	
	private String username;
	
	private String uriDesc;
	
	private String requestIp;
	
	private String requestUri;
	
	private String methodName;
	
	private String requestParams;
	
	private String exceptionInfo;
	
	private Timestamp beginTime;
	
	private Timestamp endTime;
	
	private Long useTime;
	
	private String q_beginTime;
	private String q_endTime;

}
