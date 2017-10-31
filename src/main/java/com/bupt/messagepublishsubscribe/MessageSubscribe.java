package com.bupt.messagepublishsubscribe;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MessageSubscribe {

	private static String amqpHost = "10.108.171.217";
	private static int amqpPort = 5672;
	private static String amqpUserName = "gaoyang";
	private static String amqpPassword = "gaoyang";
	// private static String vHostsName = "gaoyang_vhosts";
	private static Connection connection = null;
	private MessageProcess messageprocess = null;
	protected Logger logger = LoggerFactory.getLogger(MessageProcess.class.getName());

	// ���ڴ���connection
	public void creatConnection() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(amqpHost);
		factory.setUsername(amqpUserName);
		factory.setPassword(amqpPassword);
		factory.setPort(amqpPort);
		// factory.setVirtualHost(vHostsName);

		connection = factory.newConnection();
		logger.info("[R] Creat a Connection.....");
	}

	/**
	 * ����channel���ڽ���message
	 * 
	 * @param channelName
	 *            ���ĵ�channel
	 * @param type
	 *            exchange������
	 * @throws IOException
	 */
	public Channel creatChannel(String channelName, String type) throws IOException {
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(channelName, type);

		logger.info("[R] Creat a Channel.....");
		return channel;

	}

	public Channel creatChannel(String channelName, ExchangeType fanout) throws IOException {
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(channelName, fanout.getType());

		logger.info("[R] Creat a Channel.....");
		return channel;

	}

	// ����һ��consumer,�õ�comsumerTag,������message
	public String consumeMessage(Channel channel, String channelName) throws IOException {

		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, channelName, "");
		logger.info("[R] Bind queue and exchange.....");
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String messageType = properties.getContentType();
				process(messageType, body.toString());
			}
		};

		logger.info("[R] Creat a Consumer.....");

		String consumerTag = channel.basicConsume(queueName, true, consumer);

		logger.info("[R] Get a ConsumerTag.....");

		return consumerTag;
	}

	/**
	 * ȡ������channel
	 * 
	 * @param channel
	 * @param consumerTag
	 * @throws IOException
	 */
	public void cancelConsumer(Channel channel, String consumerTag) throws IOException {
		// ȡ��һ��������
		channel.basicCancel(consumerTag);
		logger.info("[R] Cancel a Consumer.....");
	}

	// �ر�channel
	public void closeChannel(Channel channel) throws IOException, TimeoutException {
		channel.close();
		logger.info("[R] Close a Channel.....");
	}

	// �������
	private void process(String messageType, String message) {

		logger.info("[R] Start consume message.....");
		messageprocess.processMessage(messageType, message);
		logger.info("[R] Consume message end.....");

	}

	// �ر�connection
	public void closeConnection() throws IOException {
		connection.close();
		logger.info("[R] Close a connection.....");
	}

}
