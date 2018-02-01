package estest;
/**
*Copyright 2017 com.bupt
*@author gaoyang
*@Describe TODO
*@data Date:2017年12月11日下午7:32:50
*@version v1.0
*/

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IndexRequest {

	public String index;
	public String type;
	public Map<String, Object> map;

	public IndexRequest() {
		// TODO Auto-generated constructor stub
	}

	public IndexRequest(String index, String type, Map<String, Object> map) {
		this.index = index;
		this.type = type;
		this.map = map;
	}

	@Override
	public String toString() {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> json = new HashMap<>();
		json.put("index", index);
		json.put("type", type);
		json.put("map", map);
		String result = null;
		try {
			result = objectMapper.writeValueAsString(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
