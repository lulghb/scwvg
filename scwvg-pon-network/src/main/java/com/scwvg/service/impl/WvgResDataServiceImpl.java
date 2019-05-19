package com.scwvg.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.scwvg.vo.ResVo;
import com.scwvg.mappers.WvgResDataMapper;
import com.scwvg.service.WvgResDataService;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/11 17:10
 * @desc: 
**/
@Service
public class WvgResDataServiceImpl implements WvgResDataService {
	
	@Autowired
	private WvgResDataMapper wvgResDataMapper;
	
	@Override
	public List<Map<String, Object>> queryAllCity() {
		return this.wvgResDataMapper.queryAllCity();
	}

	@Override
	public Page<Map<String, Object>> queryAll(Map<String, Object> params, Page<Map<String, Object>> page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		
		return this.wvgResDataMapper.queryByPage(params);
	}

	@Override
	public Map<String, Object> get(String res_id) {
		return this.wvgResDataMapper.get(res_id);
	}

	@Override
	@Transactional
	public void add(ResVo params) {
		Long snmpId = this.wvgResDataMapper.getSnmpMaxId();
		Long resId = this.wvgResDataMapper.getResMaxId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// SNMP信息
		Map<String, Object> snmpMap = new HashMap<String, Object>();
		snmpMap.put("snmp_id", snmpId + 1);
		snmpMap.put("snmp_read", params.getSnmp_read());
		snmpMap.put("snmp_write", params.getSnmp_write());
		snmpMap.put("telnet_user", params.getTelnet_user());
		snmpMap.put("telnet_password", params.getTelnet_password());
		snmpMap.put("telnet_user_2level", params.getTelnet_user_2level());
		snmpMap.put("telnet_password_2level", params.getTelnet_password_2level());
		snmpMap.put("city_id", null);
		snmpMap.put("res_vendor_id", null);
		snmpMap.put("res_type_id", null);
		
		// 资源附加信息
		Map<String, Object> resSubsMap = new HashMap<String, Object>();
		resSubsMap.put("res_id", resId + 1);
		resSubsMap.put("net_date", null);
		resSubsMap.put("isno_kba", null);
		resSubsMap.put("up_model", params.getUp_model());
		resSubsMap.put("double_coupbet_old", null);
		resSubsMap.put("double_couplet", params.getDouble_couplet());
		resSubsMap.put("up_manner", params.getUp_manner());
		resSubsMap.put("isno_battery", params.getIsno_battery());
		resSubsMap.put("isno_dynamicring", params.getIsno_dynamicring());
		resSubsMap.put("zd_name", params.getZd_name());
		resSubsMap.put("res_area", params.getRes_area());
		resSubsMap.put("jf_name", null);
		resSubsMap.put("totalid", null);
		
		// 资源信息
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("res_id", resId + 1);
		resMap.put("snmp_id", snmpId + 1);
		resMap.put("res_ip", params.getRes_ip());
		resMap.put("res_name", params.getRes_name());
		resMap.put("res_alias_name", params.getRes_name());
		resMap.put("res_type_id", params.getRes_type_id());
		resMap.put("res_vendor_id", params.getRes_vendor_id());
		resMap.put("res_model", params.getRes_model());
		resMap.put("res_os_name", null);
		resMap.put("res_os_version", null);
		resMap.put("res_os_realversion", null);
		resMap.put("res_state", params.getRes_state());
		resMap.put("city_id", params.getCity_id());
		resMap.put("res_addess", params.getRes_address());
		resMap.put("res_addtime", sdf.format(new Date(System.currentTimeMillis())));
		resMap.put("gather_id", "test");
		resMap.put("res_other", null);
		
		// 插入SNMP信息
		this.wvgResDataMapper.insertSnmp(snmpMap);
		// 插入资源信息
		this.wvgResDataMapper.insertRes(resMap);
		// 插入资源子信息
		this.wvgResDataMapper.insertResSubs(resSubsMap);
	}

	@Override
	@Transactional
	public void update(ResVo params) {
		Map<String, Object> map = this.wvgResDataMapper.get(String.valueOf(params.getRes_id()));
		
		// SNMP信息
		Map<String, Object> snmpMap = new HashMap<String, Object>();
		snmpMap.put("snmp_id", map.get("snmp_id"));
		snmpMap.put("snmp_read", params.getSnmp_read());
		snmpMap.put("snmp_write", params.getSnmp_write());
		snmpMap.put("telnet_user", params.getTelnet_user());
		snmpMap.put("telnet_password", params.getTelnet_password());
		snmpMap.put("telnet_user_2level", params.getTelnet_user_2level());
		snmpMap.put("telnet_password_2level", params.getTelnet_password_2level());
		snmpMap.put("city_id", null);
		snmpMap.put("res_vendor_id", null);
		snmpMap.put("res_type_id", null);
		
		// 资源附加信息
		Map<String, Object> resSubsMap = new HashMap<String, Object>();
		resSubsMap.put("res_id", params.getRes_id());
		resSubsMap.put("net_date", null);
		resSubsMap.put("isno_kba", null);
		resSubsMap.put("up_model", params.getUp_model());
		resSubsMap.put("double_coupbet_old", null);
		resSubsMap.put("double_couplet", params.getDouble_couplet());
		resSubsMap.put("up_manner", params.getUp_manner());
		resSubsMap.put("isno_battery", params.getIsno_battery());
		resSubsMap.put("isno_dynamicring", params.getIsno_dynamicring());
		resSubsMap.put("zd_name", params.getZd_name());
		resSubsMap.put("res_area", params.getRes_area());
		resSubsMap.put("jf_name", null);
		resSubsMap.put("totalid", null);
		
		// 资源信息
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("res_id", map.get("res_id"));
		resMap.put("snmp_id", map.get("snmp_id"));
		resMap.put("res_ip", params.getRes_ip());
		resMap.put("res_name", params.getRes_name());
		resMap.put("res_alias_name", params.getRes_name());
		resMap.put("res_type_id", params.getRes_type_id());
		resMap.put("res_vendor_id", params.getRes_vendor_id());
		resMap.put("res_model", params.getRes_model());
		resMap.put("res_os_name", null);
		resMap.put("res_os_version", null);
		resMap.put("res_os_realversion", null);
		resMap.put("res_state", params.getRes_state());
		resMap.put("city_id", params.getCity_id());
		resMap.put("res_addess", params.getRes_address());
		resMap.put("gather_id", "test");
		resMap.put("res_other", null);
		
		// 插入SNMP信息
		this.wvgResDataMapper.updateSnmp(snmpMap);
		// 插入资源子信息
		this.wvgResDataMapper.updateResSubs(resSubsMap);
		// 插入资源信息
		this.wvgResDataMapper.updateRes(resMap);
	}

	@Override
	@Transactional
	public void delete(Long res_id) {
		Map<String, Object> map = this.wvgResDataMapper.get(String.valueOf(res_id));
		
		Integer snmp_id = (Integer) map.get("snmp_id");
		
		this.wvgResDataMapper.deleteResSubs(res_id);
		this.wvgResDataMapper.deleteRes(res_id);
		this.wvgResDataMapper.deleteSnmp(snmp_id);
	}

}
