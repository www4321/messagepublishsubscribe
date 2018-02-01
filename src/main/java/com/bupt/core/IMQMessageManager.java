package com.bupt.core;

public interface IMQMessageManager {
	/**
	 * 功能:发布一条消息
	 * @param message :消息数据实体，包含发布消息数据类型、消息数据的内容
	 */
	public void publishMessage(Message message);
	/**
	 * 功能:订阅消息，实现对一种消息类型的监听
	 * @param messageType 订阅消息的类型
	 * @param messageProcess 当发布者发送消息后，messageProcess会接收并处理消息
	 */
	public void subscribeMessage(String messageType, IMessageProcess messageProcess);
	/**
	 * 功能:取消订阅的消息
	 * @param messageType 订阅消息的类型
	 * @param messageProcess 消息处理函数
	 */
	public void removeSubscribeMessage(String messageType, IMessageProcess messageProcess);	
}
