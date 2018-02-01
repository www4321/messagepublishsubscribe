package com.bupt.subscribe;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class SubscribeRequest {
	private String action;
	private UUID  id;
	private Map<String, String> body;
	public SubscribeRequest(){}
	public SubscribeRequest(String action, UUID id, Map<String, String> body) {
		super();
		this.action = action;
		this.id = id;
		this.body = body;
	}
	public SubscribeRequest(String action, String id, Map<String, String> body) {
		super();
		this.action = action;
		this.id = UUID.fromString(id);
		this.body = body;
	}
	public SubscribeRequest(String channel){
		this.action = "subscribe";
		this.id = UUID.randomUUID();
		this.body = new HashMap<String, String>();
		body.put("channel", channel);
		body.put("subscribe-id", UUID.randomUUID().toString());
	}
	public String toJsonString(){
		ObjectMapper objectMapper = new ObjectMapper(); 
		Map<String,String> data = new HashMap<String,String>();
		data.put("action", action);
		data.put("id", id.toString());
		String result = null;
		try {
			String content = objectMapper.writeValueAsString(body);
			data.put("body", content);
			result = objectMapper.writeValueAsString(data);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public Map<String, String> getBody() {
		return body;
	}
	public void setBody(Map<String, String> body) {
		this.body = body;
	}
	
}
