package com.scwvg.system.log.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技成都有限公司
 * @tel: 18080921750
 * @date: 2019/04/19 22:20
 * @desc: 
**/
@Entity
@Data
@Table(name = "wvg_access_operaction_log")
@NoArgsConstructor
public class WvgOperationLog {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "web_operation_id")
    private Long id;
	
	@Column(name = "wvg_login_name")
	private String username;
	
	@Column(name = "uri_desc")
	private String uriDesc;
	
	@Column(name = "request_ip")
	private String requestIp;
	
	@Column(name = "request_uri")
	private String requestUri;
	
	@Column(name = "method_name")
	private String methodName;
	
	@Column(name = "params")
	private String requestParams;
	
	@Column(name = "exception_info")
	private String exceptionInfo;
	
	@Column(name = "begin_time")
	private Timestamp beginTime;
	
	@Column(name = "end_time")
	private Timestamp endTime;
	
	@Column(name = "use_time")
	private Long useTime;

}
