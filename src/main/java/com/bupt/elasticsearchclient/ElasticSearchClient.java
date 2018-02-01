package com.bupt.elasticsearchclient;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Copyright 2017 com.bupt
 * 
 * @author gaoyang
 * @Describe TODO
 * @data Date:2017年11月19日下午6:17:18
 * @version v1.0
 */
@Component
public class ElasticSearchClient implements IMsgStorageManager {
	public static TransportClient client = null;

	@Value("${hostName}")
	private String hostName;
	Logger logger = LoggerFactory.getLogger(ElasticSearchClient.class.getName());

	public ElasticSearchClient() {

	}

	// Spring boot先调用构造方法，再将属性注入,所有在构造方法中不能直接使用属性

	@PostConstruct
	public void post() throws UnknownHostException {
		logger.info("hostName:" + hostName);
		Settings settings = Settings.settingsBuilder().build();
		client = TransportClient.builder().settings(settings).build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName), 9300));
		logger.info("MsgStorageManager has been started.");
	}

	/**
	 * 
	 * 
	 * @param hostName
	 *            ES集群中的一个或者多个主机的ip
	 * @throws UnknownHostException
	 */
	public ElasticSearchClient(String hostName) throws UnknownHostException {
		Settings settings = Settings.settingsBuilder().build();
		client = TransportClient.builder().settings(settings).build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName), 9300));

	}

	public ElasticSearchClient(Iterable<String> hostName) throws UnknownHostException {
		Settings settings = Settings.settingsBuilder().build();
		client = TransportClient.builder().settings(settings).build();
		for (String string : hostName) {
			client = client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(string), 9300));
		}
	}

	/**
	 * 向一个index中插入一个document
	 * 
	 * @param index
	 *            document所在的index
	 * @param type
	 *            document类型
	 * @param json
	 *            document实体，json格式
	 * @return 插入的document的id
	 */
	@Override
	public String index(String index, String type, Map<String, Object> map) {
		ObjectMapper mapper = new ObjectMapper();
		IndexResponse response = null;
		try {
			response = client.prepareIndex(index, type).setSource(mapper.writeValueAsBytes(map)).get();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.getId();
	}

	/**
	 * 向一个index中插入一个document
	 * 
	 * @param index
	 *            document所在的index
	 * @param type
	 *            document类型
	 * @param json
	 *            document实体，json格式
	 * @return 插入的document的id
	 */
	@Override
	public String index(String index, String type, String id, Map<String, Object> map) {
		ObjectMapper mapper = new ObjectMapper();
		IndexResponse response = null;
		try {
			response = client.prepareIndex(index, type, id).setSource(mapper.writeValueAsString(map)).get();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.getId();
	}

	/**
	 * 根据document的id查找document
	 * 
	 * @param index
	 *            document所在的index
	 * @param type
	 *            document类型
	 * @param id
	 *            document的id
	 * @return
	 */
	@Override
	public String getById(String index, String type, String id) {
		GetResponse response = client.prepareGet(index, type, id).get();
		return response.getSourceAsString();
	}

	/**
	 * 根据id删除一个document
	 * 
	 * @param index
	 *            document所在的index
	 * @param type
	 *            document的类型
	 * @param id
	 *            document的id
	 * @return true:找到该index且删除成功
	 */
	@Override
	public boolean deleteById(String index, String type, String id) {
		DeleteResponse response = client.prepareDelete(index, type, id).get();
		return response.isFound();
	}

	/**
	 * 根据id对document的内容进行更新
	 * 
	 * @param index
	 *            document所在的index
	 * @param type
	 *            document的类型
	 * @param id
	 *            document的id
	 * @param map
	 *            document需要更新的内容，key:fieldName value:fieldValue
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Override
	public void updateById(String index, String type, String id, Map<String, Object> map)
			throws IOException, InterruptedException, ExecutionException {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(index);
		updateRequest.type(type);
		updateRequest.id(id);
		Set<String> set = map.keySet();

		XContentBuilder xContentBuilder = jsonBuilder().startObject();

		for (String string : set) {
			xContentBuilder = xContentBuilder.field(string, map.get(string));
		}
		updateRequest.doc(xContentBuilder.endObject());
		client.update(updateRequest).get();
	}

	/**
	 * 得到多个id的document
	 * 
	 * @param index
	 *            document所在的index
	 * @param type
	 *            document的类型
	 * @param ids
	 *            要查询的document的ids
	 * @return
	 */
	@Override
	public List<String> multiGetById(String index, String type, Iterable<String> ids) {
		List<String> list = new ArrayList<String>();

		MultiGetResponse multiGetItemResponses = client.prepareMultiGet().add(index, type, ids).get();

		for (MultiGetItemResponse itemResponse : multiGetItemResponses) { 
		    GetResponse response = itemResponse.getResponse();
		    if (response.isExists()) {                      
		        list.add(response.getSourceAsString()); 
		    }
		}
		
		return list;
	}

	/**
	 * 在一个请求中批量插入document
	 * 
	 * @param bulkRequest
	 * @return 插入document成功的id
	 */
	@Override
	public List<String> bulkIndex(BulkRequestBuilder bulkRequest) {
		List<String> list1 = new ArrayList<String>();

		BulkResponse bulkResponse = bulkRequest.get();
		for (BulkItemResponse bulkItemResponse : bulkResponse) {
			if (bulkItemResponse.isFailed()) {
				logger.info("[ElasticSearchClient:bulkIndex] id=" + bulkItemResponse.getId() + "is failed,message is "
						+ bulkItemResponse.getFailureMessage());
			} else {
				list1.add(bulkItemResponse.getId());
			}
		}
		return list1;
	}

	/**
	 * 批量更新
	 * 
	 * @param bulkRequest
	 * @return 更新成功的id
	 */
	public List<String> bulkUpdate(BulkRequestBuilder bulkRequest) {
		List<String> list1 = new ArrayList<String>();

		BulkResponse bulkResponse = bulkRequest.get();
		for (BulkItemResponse bulkItemResponse : bulkResponse) {
			if (bulkItemResponse.isFailed()) {
				logger.info("[ElasticSearchClient:bulkUpdate] id=" + bulkItemResponse.getId() + "is failed,message is "
						+ bulkItemResponse.getFailureMessage());
			} else {
				list1.add(bulkItemResponse.getId());
			}
		}
		return list1;
	}

	/**
	 * 批量删除
	 * 
	 * @param bulkRequest
	 * @return 删除成功的document的id
	 */
	public List<String> bulkDelete(BulkRequestBuilder bulkRequest) {
		List<String> list1 = new ArrayList<String>();

		BulkResponse bulkResponse = bulkRequest.get();
		for (BulkItemResponse bulkItemResponse : bulkResponse) {
			if (bulkItemResponse.isFailed()) {
				logger.info("[ElasticSearchClient:bulkDelete] id=" + bulkItemResponse.getId() + "is failed,message is "
						+ bulkItemResponse.getFailureMessage());
			} else {
				list1.add(bulkItemResponse.getId());
			}
		}
		return list1;
	}

	/**
	 * 查找
	 * 
	 * @param indices
	 *            一或多个index
	 * @param types
	 *            一或者多个type
	 * @param queryBuilder
	 *            查询的具体条件
	 * @param postFilter
	 *            过滤
	 * @return 所有查询到的document
	 */
	public List<String> search(String index, String type, QueryBuilder queryBuilder, QueryBuilder postFilter) {
		List<String> list2 = new ArrayList<String>();
		SearchResponse response = client.prepareSearch(index) // index
				.setTypes(type) // type
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH) // Query的类型
				.setQuery(queryBuilder) // Query
				.setPostFilter(postFilter) // Filter
				.setFrom(0).setSize(60).setExplain(true).execute().actionGet();
		SearchHits searchHits = response.getHits();
		for (SearchHit Hit : searchHits) {
			list2.add(Hit.getSourceAsString());
		}

		return list2;
	}

	/**
	 * 查找
	 * 
	 * @param indices
	 *            一或多个index
	 * @param types
	 *            一或者多个type
	 * @param queryBuilder
	 *            查询的具体条件
	 * @param postFilter
	 *            过滤
	 * @return 所有查询到的document
	 */
	public List<String> search(String[] indices, String[] types, QueryBuilder queryBuilder, QueryBuilder postFilter) {
		List<String> list2 = new ArrayList<String>();
		SearchResponse response = client.prepareSearch(indices) // index
				.setTypes(types) // type
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH) // Query的类型
				.setQuery(queryBuilder) // Query
				.setPostFilter(postFilter) // Filter
				.setFrom(0).setSize(60).setExplain(true).execute().actionGet();
		SearchHits searchHits = response.getHits();
		for (SearchHit Hit : searchHits) {
			list2.add(Hit.getSourceAsString());
		}

		return list2;
	}

	/**
	 * 查找
	 * 
	 * @param indices
	 *            一或多个index
	 * @param types
	 *            一或者多个type
	 * @param queryBuilder
	 *            查询的具体条件
	 * @return 所有查询到的document
	 */

	@Override
	public List<String> search(String index, String type, QueryBuilder queryBuilder) {
		List<String> list2 = new ArrayList<String>();
		SearchResponse response = client.prepareSearch(index) // index
				.setTypes(type) // type
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH) // Query的类型
				.setQuery(queryBuilder) // Query
				.setFrom(0).setSize(60).setExplain(true).execute().actionGet();
		SearchHits searchHits = response.getHits();
		for (SearchHit Hit : searchHits) {
			list2.add(Hit.getSourceAsString());
		}

		return list2;
	}

	/**
	 * 查找
	 * 
	 * @param indices
	 *            一或多个index
	 * @param types
	 *            一或者多个type
	 * @param queryBuilder
	 *            查询的具体条件
	 * @return 所有查询到的document
	 */

	@Override
	public List<String> search(String[] indices, String[] types, QueryBuilder queryBuilder) {
		List<String> list2 = new ArrayList<String>();
		SearchResponse response = client.prepareSearch(indices) // index
				.setTypes(types) // type
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH) // Query的类型
				.setQuery(queryBuilder) // Query
				.setFrom(0).setSize(60).setExplain(true).execute().actionGet();
		SearchHits searchHits = response.getHits();
		for (SearchHit Hit : searchHits) {
			list2.add(Hit.getSourceAsString());
		}

		return list2;
	}

}
