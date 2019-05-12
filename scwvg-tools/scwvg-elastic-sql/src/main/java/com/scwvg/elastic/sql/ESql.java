package com.scwvg.elastic.sql;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/09 22:32
 * @desc: es-sql工具类
**/

public class ESql {
	
	private String clusterName;
	private boolean sniff;
	private String host;
	private ObjectEsSql support;
	private TransportClient client;
	
	public ESql(String clusterName, String host) {
		this(clusterName, host, false);
	}
	
	public ESql(String clusterName, String host, boolean sniff) {
		this.clusterName = clusterName;
		this.host = host;
		this.sniff = false;
	}
	
	/**
	 * 初始化
	 * @throws Exception
	 */
	public void init() throws Exception {
		Settings settings = Settings.builder()
				.put("cluster.name", this.clusterName)
				.put("client.transport.sniff", this.sniff)
				.build();
		client = new PreBuiltTransportClient(settings);
		String[] split = this.host.split(",");
		boolean hasHostPort = false;
		for (String s : split) {
			try {
				String[] hostAndPort = s.split(":");
				if(hostAndPort.length == 2) {
					client.addTransportAddress(new TransportAddress(InetAddress.getByName(hostAndPort[0]), Integer.parseInt(hostAndPort[1])));
					hasHostPort = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(hasHostPort) {
			support = new ObjectEsSql();
			support.setClient(client);
		} else {
			throw new RuntimeException("ESql init fail!");
		}
	}
	
	/**
	 * 关闭
	 */
	public void close() {
		try {
			this.client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 统计记录数
	 * @param sql	select count(字段) from index
	 * @return
	 * @throws Exception
	 */
	public long queryForCount(String sql) throws Exception {
		return this.support.queryForCount(sql);
	}
	
	/**
	 * 返回一条记录数
	 * @param sql	select * from index
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryForOne(String sql) throws Exception {
		return this.support.queryForOne(sql);
	}
	
	/**
	 * 列表查询
	 * @param sql	select * from index
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryForList(String sql) throws Exception {
		return this.support.queryForList(sql);
	}
	
	/**
	 * 列表分页查询
	 * @param sql	select * from index
	 * @param s	开始行索引
	 * @param r	返回的行数
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryForList(String sql, int s, int r) throws Exception {
		return this.support.queryForList(sql, s, r);
	}
	
	/**
	 * 分组查询
	 * @param sql	SELECT fieldA,sum(fieldB) as total FROM index group by fieldA
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryForListGroup(String sql) throws Exception {
		return this.support.queryForListGroup(sql);
	}
	
	/**
	 * 分页分组查询
	 * @param sql	SELECT fieldA,sum(fieldB) as total FROM index group by fieldA
	 * @param r	开始行索引
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryForListGroup(String sql, int r) throws Exception {
		return this.support.queryForListGroup(sql, r);
	}
	
	
	public String getClusterName() {
		return clusterName;
	}
	
	/**
	 * 集群名称
	 * @param clusterName
	 */
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public boolean isSniff() {
		return sniff;
	}
	/**
	 * 是否嗅探
	 * @return
	 */
	public void setSniff(boolean sniff) {
		this.sniff = sniff;
	}
	public String getHost() {
		return host;
	}
	/**
	 * 主机
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}
	

}
