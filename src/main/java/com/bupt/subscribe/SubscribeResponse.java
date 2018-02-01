package com.bupt.subscribe;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SubscribeResponse {
	private String action;
	private UUID uuid;
	public SubscribeResponse(String action, UUID uuid) {
		super();
		this.action = action;
		this.uuid = uuid;
	}

	public String toJsonString() {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("action", action);
		data.put("id", uuid.toString());
		String content = null;
		ObjectMapper objectMapper = new ObjectMapper(); 
		try {
			content =objectMapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return content;
	}
	

}
