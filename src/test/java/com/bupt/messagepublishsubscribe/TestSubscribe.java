package com.bupt.messagepublishsubscribe;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;

import com.bupt.messagepublishsubscribe.ExchangeType;
import com.bupt.messagepublishsubscribe.MessageSubscribe;

public class TestSubscribe implements Runnable {

	public void start() throws IOException, TimeoutException {
		MessageSubscribe messageSubscribe = new MessageSubscribe();
		messageSubscribe.creatConnection();
		Channel channel = messageSubscribe.creatChannel("test1", ExchangeType.FANOUT);
		String consumerTag = messageSubscribe.consumeMessage(channel, "test1");
		// messageSubscribe.consumeMessage(channel, consumerTag);
		// messageSubscribe.closeChannel(channel);
		// messageSubscribe.closeChannel(channel);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			start();
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TestSubscribe testSubscribe = new TestSubscribe();
		new Thread(testSubscribe).start();
	}

}
