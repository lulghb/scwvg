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
@Table(name = "wvg_access_login_log")
@NoArgsConstructor
public class WvgLoginLog {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "web_log_id")
    private Long id;
	
	@Column(name = "wvg_login_name")
	private String username;
	
	@Column(name = "web_log_ipaddess")
	private String loginIp;
	
	@Column(name = "web_login_status")
	private Long status;
	
	@Column(name = "web_log_desc")
	private String desc;
	
	@Column(name = "web_log_session")
	private String loginSession;
	
	@Column(name = "web_log_time")
	private Timestamp loginTime;
	
}
