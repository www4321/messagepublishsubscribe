package com.bupt.messagepublishsubscribe;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;

import com.bupt.messagepublishsubscribe.ExchangeType;
import com.bupt.messagepublishsubscribe.MessagePublish;

public class TestPublish implements Runnable {

	public String message = "www.baidu.com|404 not found|500|504!";

	public void start() throws IOException, TimeoutException {
		MessagePublish messagePublish = new MessagePublish();
		messagePublish.creatConnection();
		Channel channel = messagePublish.creatChannel("test1", ExchangeType.FANOUT);
		messagePublish.publish(channel, "test1", "text/plain", message);
		// messagePublish.deleteChannel(channel, "test1");
		// messagePublish.closeConnection();
	}

	@Override
	public void run() {
		try {
			start();
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TestPublish testPublish = new TestPublish();
		new Thread(testPublish).start();
	}

}
