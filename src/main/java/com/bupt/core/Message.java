package com.bupt.core;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	
    public String channel;
	public String message = null;
	public Message() {}
	public Message(String channel,String message) {
		this.channel = channel;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
