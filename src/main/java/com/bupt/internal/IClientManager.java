package com.bupt.internal;

import java.util.List;

import io.netty.channel.Channel;

public interface IClientManager {
	public List<Channel> getClientEntityByType(String type);

}
