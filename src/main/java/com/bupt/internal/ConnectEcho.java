package com.bupt.internal;

import java.io.Serializable;
import java.util.UUID;

public class ConnectEcho implements Serializable {

	
	private static final long serialVersionUID = 12340000000000000L;
	
	private UUID channelId;
	private String channel;
	public ConnectEcho(UUID channelId, String channel) {
		super();
		this.channelId = channelId;
		this.channel = channel;
	}
	public UUID getChannelId() {
		return channelId;
	}
	public void setChannelId(UUID channelId) {
		this.channelId = channelId;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
		
	}
	public static void main(String []args){
		System.out.println(UUID.randomUUID().toString());
		System.out.println();
	}
	@Override
	public String toString() {
		return "ConnectEcho [channelId=" + channelId + ", channel=" + channel + "]";
	}
	

}
