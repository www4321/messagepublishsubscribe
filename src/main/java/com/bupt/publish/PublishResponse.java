package com.bupt.publish;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PublishResponse {
	private String action;
	private UUID uuid;
	private String channel;
	public PublishResponse(String action, UUID uuid,String channel) {
		super();
		this.channel = channel;
		this.action = action;
		this.uuid = uuid;
	}

	public String toJsonString() {
		Map<String,String> data = new HashMap<String,String>();
		data.put("action", action);
		data.put("id", uuid.toString());
		data.put("channel", channel);
		String content = null;
		ObjectMapper objectMapper = new ObjectMapper(); 
		try {
			content =objectMapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return content;
	}
	public PublishResponse(String sublishResponse){
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode root = objectMapper.readTree(sublishResponse);
			System.out.println(root.toString());
			this.action = root.findValue("action").asText();
			this.uuid = UUID.fromString(root.findValue("id").asText());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

}
