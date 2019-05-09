package com.scwvg.elastic.sql;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.nlpcn.es4sql.domain.Field;
import org.nlpcn.es4sql.domain.KVValue;
import org.nlpcn.es4sql.domain.MethodField;
import org.nlpcn.es4sql.domain.Order;
import org.nlpcn.es4sql.domain.Select;
import org.nlpcn.es4sql.domain.Where;
import org.nlpcn.es4sql.exception.SqlParseException;
import org.nlpcn.es4sql.query.AggregationQueryAction;
import org.nlpcn.es4sql.query.maker.AggMaker;
import org.nlpcn.es4sql.query.maker.QueryMaker;

/**
 * @author: tangyl
 * @unit: 智谷园网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/09 20:32
 * @desc: es-sql工具类
**/
public class WvgAggregationQueryAction extends AggregationQueryAction {
	
	private final Select select;
	private AggMaker aggMaker = new AggMaker();
	private List<Field> fields;
	private SearchRequestBuilder request;

	public WvgAggregationQueryAction(Client client, Select select) {
		super(client, select);
		this.select = select;
	}
	
	public SearchRequestBuilder explain(int r) throws SqlParseException {
		this.request = this.client.prepareSearch(new String[0]);

		this.request.setTrackScores(false);

		setIndicesAndTypes();

		setWhere(this.select.getWhere());
		AggregationBuilder lastAgg = null;
		if (this.select.getGroupBys().get(0).size() > 0) {
			fields = this.select.getGroupBys().get(0);
			Field field = fields.get(0);
			lastAgg = this.aggMaker.makeGroupAgg(field);

			if ((lastAgg != null) && ((lastAgg instanceof TermsAggregationBuilder))) {
				((TermsAggregationBuilder) lastAgg).size(r);
			}

			this.request.addAggregation(lastAgg);
			for (int i = 1; i < fields.size(); i++) {
				field = fields.get(i);
				AggregationBuilder subAgg = this.aggMaker.makeGroupAgg(field);
				if ((subAgg instanceof TermsAggregationBuilder)) {
					((TermsAggregationBuilder) subAgg).size(r);
				}

				lastAgg.subAggregation(subAgg);
				lastAgg = subAgg;
			}
		}

		Map<String, KVValue> groupMap = this.aggMaker.getGroupMap();

		if (this.select.getFields().size() > 0)
			
			explanFields(this.request, this.select.getFields(), lastAgg);
		KVValue temp;
		TermsAggregationBuilder termsBuilder;
		if ((lastAgg != null) && (this.select.getOrderBys().size() > 0)) {
			temp = null;
			termsBuilder = null;
			for (Order order : this.select.getOrderBys()) {
				temp = groupMap.get(order.getName());
				termsBuilder = (TermsAggregationBuilder) temp.value;
				switch (temp.key) {
				case "COUNT":
					termsBuilder.order(BucketOrder.count(isASC(order)));
					break;
				case "KEY":
					termsBuilder.order(BucketOrder.key(isASC(order)));
					break;
				case "FIELD":
					termsBuilder.order(BucketOrder.aggregation(order.getName(), isASC(order)));
					break;
				default:
					throw new SqlParseException(order.getName() + " can not to order");
				}
			}
		}
		this.request.setSize(0);
		this.request.setSearchType(SearchType.DEFAULT);
		return this.request;
	}
	
	private boolean isASC(Order order) {
		return "ASC".equals(order.getType());
	}

	public List<Field> getFields() {
		return fields;
	}
	
	private void explanFields(SearchRequestBuilder request, List<Field> fields, AggregationBuilder groupByAgg)
			throws SqlParseException {
		for (Field field : fields)
			if ((field instanceof MethodField)) {
				AggregationBuilder makeAgg =  this.aggMaker.makeFieldAgg((MethodField) field, groupByAgg);
				if (groupByAgg != null) {
					groupByAgg.subAggregation(makeAgg);
				} else
					request.addAggregation(makeAgg);
			} else if ((field instanceof Field)) {
				request.addStoredField(field.getName());
			} else {
				throw new SqlParseException("it did not support this field method " + field);
			}
	}
	
	private void setWhere(Where where) throws SqlParseException {
		if (where != null) {
			BoolQueryBuilder boolFilter = QueryMaker.explan(where); 
			this.request.setQuery(boolFilter);
		}
	}
	
	private void setIndicesAndTypes() {
		this.request.setIndices(this.query.getIndexArr());

		String[] typeArr = this.query.getTypeArr();
		if (typeArr != null)
			this.request.setTypes(typeArr);
	}

}
