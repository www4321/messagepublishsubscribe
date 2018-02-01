package com.bupt.internal;

import java.util.UUID;

import io.netty.channel.Channel;

public class ClientEntity {
	private UUID clientId;
	private Channel channel;
	private String type;
	public UUID getClientId() {
		return clientId;
	}
	public void setClientId(UUID clientId) {
		this.clientId = clientId;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ClientEntity(UUID clientId, Channel channel, String type) {
		super();
		this.clientId = clientId;
		this.channel = channel;
		this.type = type;
	}
	@Override
	public String toString() {
		return "ClientEntity [clientId=" + clientId + ", channel=" + channel + ", type=" + type + "]";
	}
	
}
