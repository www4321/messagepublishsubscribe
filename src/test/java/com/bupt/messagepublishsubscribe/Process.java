package com.bupt.messagepublishsubscribe;

public class Process implements MessageProcess {

	@Override
	public void processMessage(String messageType, String message) {
		System.out.println("[x] This is true process...");
	}

}
