package com.bupt.publish;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PublishRequest {
	private String action;
	private UUID  id;
	private Map<String, String> body;
	public PublishRequest() {

	}
	public PublishRequest(String action, UUID id, Map<String, String> body) {
		super();
		this.action = action;
		this.id = id;
		this.body = body;
	}
	public PublishRequest(String channel, String message) {
		super();
		this.action = "publish";
		this.id = UUID.randomUUID();
		this.body = new HashMap<String, String>();
		body.put("channel", channel);
		body.put("message", message);
	}
	public String toJsonString() {
		ObjectMapper objectMapper = new ObjectMapper(); 
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("action", action);
		data.put("id", id.toString());
		data.put("body", body);
		String result = null;
		try {

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
