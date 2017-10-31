package com.bupt.messagepublishsubscribe;

public enum ExchangeType {

	// ����ֻʹ��fanout����
	DIRECT("direct"), FANOUT("fanout"), TOPIC("topic"), HEADERS("headers");

	private final String type;

	ExchangeType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
