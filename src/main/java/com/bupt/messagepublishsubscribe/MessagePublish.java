package com.bupt.messagepublishsubscribe;

//������������message

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessagePublish {

	private static String amqpHost = "10.108.171.217";
	private static int amqpPort = 5672;
	private static String amqpUserName = "gaoyang";
	private static String amqpPassword = "gaoyang";
	// private static String vHostsName = "gaoyang_vhosts";
	private static Connection connection = null;

	protected Logger logger = LoggerFactory.getLogger(MessagePublish.class.getName());

	// ���ڴ���һ��connection
	public void creatConnection() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(amqpHost);
		factory.setUsername(amqpUserName);
		factory.setPassword(amqpPassword);
		factory.setPort(amqpPort);
		// factory.setVirtualHost(vHostsName);
		connection = factory.newConnection();
		logger.info("[x] Create a Connection.....");
	}

	/**
	 * �÷������ڴ���channel(exchange)
	 * 
	 * @param channelName
	 *            ��channel��Ӧ��exchange������
	 * @param type
	 *            exchange������
	 * @return channel ������channel
	 * @throws IOException
	 */
	public Channel creatChannel(String channelName, String type) throws IOException {
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(channelName, type);
		logger.info("[x] Creat a channel.....");
		return channel;
	}

	/**
	 * @param channelName
	 *            exchange������
	 * @param fanout
	 *            exchange����
	 */
	public Channel creatChannel(String channelName, ExchangeType fanout) throws IOException {
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(channelName, fanout.getType());
		logger.info("[x] Creat a channel.....");
		return channel;
	}

	/**
	 * publish����������exchange����message
	 * 
	 * @param chanel
	 *            : message������channel
	 * @param channelName
	 *            :exchange������
	 * @param messagetype
	 *            :Ҫ��������Ϣ������
	 * @param message
	 *            :��������Ϣ����
	 */
	public void publish(Channel channel, String channelName, String messagetype, String message) throws IOException {
		// ������������message���ԣ�
		channel.basicPublish(channelName, "", new AMQP.BasicProperties().builder().contentType(messagetype).build(),
				message.getBytes());
		logger.info("[x] Publish a message.....");

	}

	/**
	 * ����ɾ��һ��channel
	 * 
	 * @param channel
	 *            Ҫɾ����channel
	 * @param channelName
	 *            ���Ӧ��exchange
	 * @throws TimeoutException
	 */
	public void deleteChannel(Channel channel, String channelName) throws IOException, TimeoutException {
		// ɾ��exchange
		channel.exchangeDelete(channelName);
		// �ر�channel
		channel.close();
		logger.info("[x] Close a channel.....");
	}

	/**
	 * ���ڹر�����
	 */
	public void closeConnection() throws IOException {
		connection.close();
		logger.info("[x] Close a connection");
	}

}
