package com.scwvg.elastic.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.InternalTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.InternalNumericMetricsAggregation.SingleValue;
import org.nlpcn.es4sql.domain.Field;
import org.nlpcn.es4sql.query.SqlElasticDeleteByQueryRequestBuilder;
import org.nlpcn.es4sql.query.SqlElasticSearchRequestBuilder;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/09 20:32
 * @desc: es-sql工具类
**/
public class ObjectEsSql implements WvgEsSql {
	
	private final static Pattern UN_P = Pattern.compile("[ ]+[uU][nN][iI][oO][nN][ ]+([aA][lL][lL])?");
	
	private Client client;

	@Override
	public long queryForCount(String sql) throws Exception {
		check();
		
		SearchResponse x = (SearchResponse) createSearchRequestBuilder(client, sql).get();

		SearchHits shs = x.getHits();
		long size = shs.getTotalHits();
		return size;
	}

	@Override
	public long deleteBySql(String sql) throws Exception {
		check();
		// must set size, otherwise get 200 records only
		SqlElasticDeleteByQueryRequestBuilder x = (SqlElasticDeleteByQueryRequestBuilder) (WvgEsActionFactory
				.create(client, sql)).explain();
		ActionResponse ret = x.get();
		if (ret.toString().contains("deleted=0")) {
			return 0;
		}
		return 1;
	}

	@Override
	public Map<String, Object> queryForOne(String sql) throws Exception {
		check();
		
		SearchResponse x = (SearchResponse) createSearchRequestBuilder(client, sql).get();

		SearchHits shs = x.getHits();
		long size = shs.getTotalHits();
		if (size < 1)
			return null;
		return  shs.getHits()[0].getSourceAsMap();
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) throws Exception {
		return queryForList(sql, 0, 10000);
	}

	@Override
	public List<Map<String, Object>> queryForListUnion(String sql) throws Exception {
		check();
		
		if (unionExists(sql)) {
			String[] sqlArray = splitSql(sql);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (String s : sqlArray) {
				list.addAll(queryForList(s));
			}
			return list;
		} else {
			return queryForList(sql);
		}
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql, int s, int r) throws Exception {
		check();
		
		SqlElasticSearchRequestBuilder x = (SqlElasticSearchRequestBuilder) createSearchRequestBuilder(client, sql);
		SearchRequestBuilder y = (SearchRequestBuilder) x.getBuilder();
		y.setFrom(s).setSize(r);
		return mappers(y);
	}

	@Override
	public List<Map<String, Object>> queryForListGroup(String sql) throws Exception {
		return queryForListGroup(sql,  200);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<String, Object>> queryForListGroup(String sql, int r) throws Exception {
		check();
		
		WvgAggregationQueryAction aqa = (WvgAggregationQueryAction) WvgEsActionFactory.create(client, sql);
		SearchRequestBuilder srb = aqa.explain(r);
		if (r != Integer.MIN_VALUE)
			srb.setFrom(0);
		SearchResponse sr = srb.get();
		Mapp parent = null;
		List<Mapp> mapps = new ArrayList<>();
		for (Aggregation agt : sr.getAggregations().asList()) {
			if (agt instanceof InternalTerms) {
				InternalTerms terms = (InternalTerms) agt;
				Collection<Bucket> buckets = terms.getBuckets();
				for (Bucket b : buckets) {
					parent = new Mapp();
					parent.key = terms.getName();
					parent.value = b.getKeyAsString();
					loopBucket(mapps, parent, terms, b);
				}
			} else if (agt instanceof SingleValue) {
				SingleValue sv = (SingleValue) agt;
				Mapp mapp = new Mapp();
				mapp.key = sv.getName();
				mapp.value = String.valueOf(sv.value());
				mapp.parent = parent;
			}
		}
		List<Field> fields = aqa.getFields();
		// merge
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Map<String, Object>> retMap = new LinkedHashMap<String, Map<String, Object>>();
		for (Mapp mapp : mapps) {
			String key = "";
			Map<String, Object> dataMap = mapp.entry();
			for (Field f : fields) {
				if (dataMap.containsKey(f.getName())) {
					key += dataMap.get(f.getName()) + "-";
				}
			}
			if (IsEmpty(key)) {
				continue;
			}

			if (retMap.containsKey(key)) {
				retMap.get(key).putAll(dataMap);
			} else {
				retMap.put(key, dataMap);
			}
		}
		list.addAll(retMap.values());
		mapps.clear();
		mapps = null;
		retMap.clear();
		retMap = null;
		return list;
	}

	@Override
	public void setClient(Client client) {
		this.client = client;
	}
	
	public static boolean IsEmpty(String tem) {
		return IsEmpty(tem, true);
	}
	public static boolean IsEmpty(String tem, boolean flag) {
		if (tem == null) {
			return true;
		}
		if (true == flag) {
			if (tem.trim().length() == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			if (tem.length() == 0) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	private static boolean unionExists(String sql) {
		Matcher m = UN_P.matcher(sql);
		if (m.find()) {
			sql = sql.replaceAll(UN_P.pattern(), "union");
		}
		return sql.contains("union");
	}
	
	private static String[] splitSql(String sql) {
		String[] sqlArray = sql.split("union");
		return sqlArray;
	}
	
	private void check() {
		if (this.client == null)
			throw new NullPointerException("please inject client transport!!!");
	}
	
	private SqlElasticSearchRequestBuilder createSearchRequestBuilder(
			Client client, String sql) throws Exception {

		return (SqlElasticSearchRequestBuilder) (WvgEsActionFactory.create(client,
				sql)).explain();
	}
	
	private List<Map<String, Object>> mappers(SearchRequestBuilder srb) {
		check();
		SearchHits shs = srb.get().getHits();
		List<Map<String, Object>> list = new ArrayList<>((int) shs.getTotalHits());
		Iterator<SearchHit> it = shs.iterator();
		while (it.hasNext())
			list.add(it.next().getSourceAsMap());
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void loopBucket(List<Mapp> mapps, Mapp parent, InternalTerms terms,
			Bucket bucket) {
		List<Aggregation> aggs = bucket.getAggregations().asList();
		if (aggs.isEmpty()){
			mapps.add(parent);
			return;
		}
		
		for (Aggregation agg : aggs) {
			
			if (agg instanceof InternalTerms) {
				InternalTerms it = (InternalTerms) agg;
				List<Bucket> buckets = it.getBuckets();
				for (Bucket bk : buckets) {
					Mapp cm = new Mapp();
					cm.key = it.getName();
					cm.value = bk.getKeyAsString();
					cm.parent = parent;
					loopBucket(mapps, cm, it, bk);
				}

			} else if (agg instanceof SingleValue) {
			
				SingleValue sv = (SingleValue) agg;
				Mapp cm = new Mapp();
				cm.key = sv.getName();
				cm.value = String.valueOf(sv.value());
				cm.parent = parent;
				mapps.add(cm);
			}
		}
	}
	
	class Mapp {

		public Mapp parent;
		public String key;
		public String value;

		public Mapp() {
		}

		public Mapp(String key, String value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public String toString() {
			return "{ parent : " + parent + ", key : " + key + ", value : "
					+ value + " }";
		}

		public Map<String, Object> entry() {
			Map<String, Object> map = new HashMap<>();
			map.put(key, value);
			if (parent != null) {
				map.put(parent.key, parent.value);
				loop(map, parent);
			}
			return map;
		}

		private void loop(Map<String, Object> map, Mapp parent) {
			if (parent.parent != null) {
				map.put(parent.parent.key, parent.parent.value);
				loop(map, parent.parent);
			}
		}
	}
	
}
