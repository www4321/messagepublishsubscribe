package com.bupt.elasticsearchclient;
/**
*Copyright 2017 com.bupt
*@author gaoyang
*@Describe TODO
*@data Date:2017年12月11日下午8:04:40
*@version v1.0
*/

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BulkIndexRequest {
	public String index;
	public String type;
	public List<Map<String, Object>> list;

	public BulkIndexRequest() {
		// TODO Auto-generated constructor stub
	}

	public BulkIndexRequest(String index, String type, List<Map<String, Object>> list) {
		super();
		this.index = index;
		this.type = type;
		this.list = list;
	}

	@Override
	public String toString() {
		Map<String, Object> map = new HashMap<>();
		map.put("index", index);
		map.put("type", type);
		map.put("list", list);

		ObjectMapper objectMapper = new ObjectMapper();
		String result = null;

		try {
			result = objectMapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}
}
