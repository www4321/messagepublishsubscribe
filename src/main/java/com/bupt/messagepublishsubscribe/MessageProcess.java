package com.bupt.messagepublishsubscribe;

public abstract interface MessageProcess {
	public void processMessage(String messageType, String message);
}
