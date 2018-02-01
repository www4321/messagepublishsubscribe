package estest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bupt.utils.HttpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Copyright 2017 com.bupt
 * 
 * @author gaoyang
 * @Describe TODO
 * @data Date:2017年12月11日下午12:46:46
 * @version v1.0
 */
public class Test {

	public static void main(String[] args) throws JsonProcessingException {

		Map<String, Object> map1 = new HashMap<>();
		map1.put("name", "2");
		map1.put("age", "2");
		map1.put("postDate", new Date());

		Map<String, Object> map = new HashMap<>();
		map.put("name", "2");
		map.put("age", "2");
		map.put("postDate", new Date());

		// 插入一条数据
		/*
		 * IndexRequest indexRequest = new IndexRequest("test", "security",
		 * map);
		 * System.out.println(HttpUtils.httpPost("http://127.0.0.1:8080/index",
		 * null, indexRequest.toString()));
		 */

		// 批量插入
		/*
		 * List<Map<String, Object>> list = new ArrayList<>(); list.add(map);
		 * list.add(map1); BulkIndexRequest bulkIndexRequest = new
		 * BulkIndexRequest("test", "security", list);
		 * System.out.println(HttpUtils.httpPost(
		 * "http://127.0.0.1:8080/bulkIndex", null,
		 * bulkIndexRequest.toString()));
		 */

		// 得到count条数据
		Map<String, Object> map3 = new HashMap<>();
		map3.put("index", "twitter_raw");
		map3.put("type", "chester250_raw");
		map3.put("count", 10);
		ObjectMapper objectMapper = new ObjectMapper();
		// System.out.println(objectMapper.writeValueAsString(map3));
		String result = HttpUtils.httpPost("http://127.0.0.1:8080/getLastIndex", null,
				objectMapper.writeValueAsString(map3));
		System.out.println(result);

		// 测试
		System.out.println(HttpUtils.httpGet("http://127.0.0.1:8080/home", null));
	}
}
