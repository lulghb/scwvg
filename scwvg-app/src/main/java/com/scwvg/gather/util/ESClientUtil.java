package com.scwvg.gather.util;

import java.net.InetAddress;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * es客户端工具类
 *
 * @author tangyonglin
 */
public class ESClientUtil {

    private final static Logger log = LoggerFactory.getLogger(ESClientUtil.class);

    /**
     * 主机
     */
    private String hosts;
    /**
     * es集群名称
     */
    private String clusterName;
    /**
     * 是否启用嗅探
     */
    private boolean sniff = false;
    /**
     * 是否连接
     */
    private boolean connectFlag = false;

    /**
     * es客户端
     */
    private TransportClient client;

    public static void main(String[] args) {
        Settings settings = Settings.builder()
                .put("cluster.name", "aaa")
                .put("client.transport.sniff", false).build();
        System.out.println(settings);
    }

    /**
     * 初始化es客户端
     *
     * @throws Exception
     */
    public void init() throws Exception {
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
                .put("client.transport.sniff", this.sniff)
                .put("client.transport.ping_timeout", "300s").build();
        if (!StringUtils.isEmpty(hosts)) {
            String[] ipAarry = hosts.split(",");
            client = new PreBuiltTransportClient(settings);
            for (String str : ipAarry) {
                if (str.contains(":")) {
                    TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(str.substring(0, str.indexOf(":"))), Integer.parseInt(str.substring(str.indexOf(":") + 1)));
                    client.addTransportAddress(transportAddress);
                }
            }
            connectFlag = true;
            log.info(String.format("%s %s", hosts, "es初始化完成"));

        } else {
            log.info("es主机不能为空!");
        }
    }

    /**
     * 创建Index
     *
     * @param index
     * @param type
     * @param settings
     * @param json
     * @return
     */
    public boolean createIndex(String index, Settings settings, Map<String, String> mapping) {
        if (!connectFlag) {
            throw new RuntimeException("es未初始化！");
        }

        IndicesAdminClient indices = this.client.admin().indices();
        //判断index是否存在
        if (!exists(new String[]{index})) {//index不存在, 创建index，创建mapping
            CreateIndexRequestBuilder requestBuilder = indices.prepareCreate(index).setSettings(settings);
            for (Map.Entry<String, String> entry : mapping.entrySet()) {

                String _type = entry.getKey();
                String _value = entry.getValue();

                JSONObject json = new JSONObject();
                json.put(_type, _value);

                requestBuilder.addMapping(_type, json.toString());
            }

            return requestBuilder.execute().actionGet().isAcknowledged();
        }
        log.info("Index: {}已存在！", index);
        return false;
    }

    /**
     * 创建mapping
     *
     * @param index
     * @param type
     * @param mapping
     * @return
     */
    public boolean createMapping(String index, String type, String mapping) {
        if (!connectFlag) {
            throw new RuntimeException("es未初始化！");
        }
        IndicesAdminClient indices = this.client.admin().indices();
        PutMappingRequest mappingRequest = Requests.putMappingRequest(index);
        JSONObject json = new JSONObject();
        json.put(type, mapping);
        mappingRequest.type(type).source(json.toString());
        return indices.putMapping(mappingRequest).actionGet().isAcknowledged();
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public boolean delIndex(String index) {
        IndicesAdminClient indices = this.client.admin().indices();
        return indices.prepareDelete(index).execute().actionGet().isAcknowledged();
    }

    /**
     * index是否存在
     *
     * @param indexs
     * @return
     */
    public boolean exists(String[] indexs) {
        if (!connectFlag) {
            throw new RuntimeException("es未初始化！");
        }
        return this.client.admin().indices().prepareExists(indexs).execute().actionGet().isExists();
    }

    /**
     * 判断指定的索引的类型是否存在
     *
     * @param indexName 索引名
     * @param indexType 索引类型
     * @return 存在：true; 不存在：false;
     */
    public boolean exists(String indexName, String indexType) {
        if (!connectFlag) {
            throw new RuntimeException("es未初始化！");
        }
        IndicesAdminClient indices = this.client.admin().indices();
        TypesExistsResponse response = indices.typesExists(new TypesExistsRequest(new String[]{indexName}, indexType)).actionGet();
        return response.isExists();
    }

    /**
     * 获取Bulk
     *
     * @return
     */
    public BulkRequestBuilder getBulkRequestBuilder() {
        return this.client.prepareBulk();
    }

    /**
     * 创建IndexRequestBuilder对象
     *
     * @param index 索引
     * @param type  mapping
     * @param id    主键
     * @param json  数据
     * @return
     */
    public IndexRequestBuilder getIndexRequestBuilder(String index, String type, String id, String json) {
        return getIndexRequestBuilder(index, type, id, json, null);
    }

    /**
     * 创建IndexRequestBuilder对象
     *
     * @param index 索引
     * @param type  mapping
     * @param json  数据
     * @return
     */
    public IndexRequestBuilder getIndexRequestBuilder(String index, String type, String json) {
        return getIndexRequestBuilder(index, type, null, json, null);
    }

    /**
     * 创建IndexRequestBuilder对象
     *
     * @param index 索引
     * @param type  mapping
     * @param id    主键
     * @param map   数据
     * @return
     */
    public IndexRequestBuilder getIndexRequestBuilder(String index, String type, String id, Map<String, Object> map) {
        return getIndexRequestBuilder(index, type, id, map, null);
    }

    /**
     * 创建IndexRequestBuilder对象
     *
     * @param index 索引
     * @param type  mapping
     * @param map   数据
     * @return
     */
    public IndexRequestBuilder getIndexRequestBuilder(String index, String type, Map<String, Object> map) {
        return getIndexRequestBuilder(index, type, null, map, null);
    }

    /**
     * 创建IndexRequestBuilder对象
     *
     * @param index    索引
     * @param type     mapping
     * @param map      数据
     * @param routings 路由
     * @return
     */
    public IndexRequestBuilder getIndexRequestBuilder(String index, String type, Map<String, Object> map, String[] routings) {
        return getIndexRequestBuilder(index, type, null, map, routings);
    }

    /**
     * 创建IndexRequestBuilder对象
     *
     * @param index    索引
     * @param type     mapping
     * @param id       主键
     * @param json     数据
     * @param routings 路由
     * @return
     */
    public IndexRequestBuilder getIndexRequestBuilder(String index, String type, String id, String json, String[] routings) {

        if (StringUtils.isEmpty(id)) {//如果ID为空，使用uuid
            id = UUID.randomUUID().toString();
        }

        IndexRequestBuilder builder = client.prepareIndex(index, type, id);

        if (routings != null && routings.length > 0) {
            for (String routing : routings) {//指定路由
                builder.setRouting(routing);
            }
        }

        builder.setSource(json);
        return builder;
    }

    /**
     * 创建IndexRequestBuilder对象
     *
     * @param index    索引
     * @param type     mapping
     * @param id       主键
     * @param map      数据
     * @param routings 路由
     * @return
     */
    public IndexRequestBuilder getIndexRequestBuilder(String index, String type, String id, Map<String, Object> map, String[] routings) {

        if (StringUtils.isEmpty(id)) {//如果ID为空，使用uuid
            id = UUID.randomUUID().toString();
        }

        IndexRequestBuilder builder = client.prepareIndex(index, type, id);

        if (routings != null && routings.length > 0) {
            for (String routing : routings) {//指定路由
                builder.setRouting(routing);
            }
        }

        builder.setSource(map);
        return builder;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setSniff(boolean sniff) {
        this.sniff = sniff;
    }

}
