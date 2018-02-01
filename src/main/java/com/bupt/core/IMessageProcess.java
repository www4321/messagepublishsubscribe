package com.bupt.core;

public interface IMessageProcess {
	/**
	 * 功能:实现对消息数据的处理，其他模块需要实现该接口以实现对消息的处理
	 * @param message 消息数据
	 * 
	 */
	public void messageProcess(Message message);

}
