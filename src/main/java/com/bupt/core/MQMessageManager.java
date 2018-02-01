package com.bupt.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

import com.bupt.globalconfig.GlobalConfig;
import com.rabbitmq.client.AMQP.BasicProperties;

@Component
public class MQMessageManager implements Runnable,IMQMessageManager{
	
	protected Logger logger = LoggerFactory.getLogger(MQMessageManager.class);	
	private static String TOPIC_EXCHANGE_NAME = "topic_securitycontroller";
	private static String FANOUT_EXCHANGE_NAME = "fanout_securitycontroller";
	private static String ampqHost = "127.0.0.1";
	private static int ampqPort = 5672;
	private static String ampqUserName = "guest";
	private static String ampqPassword = "guest";

	private static final String TOPIC_HEADER = "EVENT_TYPE.";

	Channel channel = null;
	QueueingConsumer consumer = null;
	Delivery delivery = null;
	
	private String topicQueueName;
	private String fanoutQueueName;

	static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
	
	protected Map<String, Set<IMessageProcess>> eventListeners;
	
	public MQMessageManager() {
		logger.info("MQ constructing...");
		ConnectionFactory factory = new ConnectionFactory();
		GlobalConfig config = GlobalConfig.getInstance();
		config.loadConfig("GlobalValues.config");
		ampqHost = config.amqpHost;
		ampqPort = Integer.parseInt(config.amqpPort);
		ampqUserName = config.amqpUserName;
		ampqPassword = config.amqpPassword;
		factory.setHost(ampqHost);
		factory.setPort(ampqPort);
		factory.setUsername(ampqUserName);
		factory.setPassword(ampqPassword);
		try {
			Connection connection = factory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, "topic");
			channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, "fanout");
			consumer = new QueueingConsumer(channel);
			topicQueueName = channel.queueDeclare().getQueue();

			fanoutQueueName = channel.queueDeclare().getQueue();
			logger.info("topicQueueName"+topicQueueName);
			logger.info("fanoutQueueName"+fanoutQueueName);
			channel.basicConsume(topicQueueName, true, consumer);
			channel.basicConsume(fanoutQueueName, true, consumer);
			channel.queueBind(fanoutQueueName, FANOUT_EXCHANGE_NAME, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.eventListeners = new HashMap<String, Set<IMessageProcess>>();
		new Thread(this).start();
	}

	@Override
	public void run() {
		logger.info("MQ eventmanager starting...");
		while (true) {
			try {
				delivery = consumer.nextDelivery();
				byte[] eventBytes = delivery.getBody();
				BasicProperties props = delivery.getProperties();
				String messageType = props.getContentType();
				//logger.info("messageType:" + messageType);
				if (messageType.equals("EVENT") || messageType.equals("BROADCAST_EVENT")) {
					Message event = (Message) bytesToObj(eventBytes);
					//logger.info("event process...................");
					processEvent(event);
				}
			} catch (IOException | ShutdownSignalException | ConsumerCancelledException | InterruptedException
					| ClassNotFoundException e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
			}
		}
		
	}
	// 添加一个订阅者
	@Override
	public void subscribeMessage(String type, IMessageProcess listener) {
		if (!this.eventListeners.containsKey(type)) {
			this.eventListeners.put(type, new HashSet<IMessageProcess>());
			try {
				channel.queueBind(topicQueueName, TOPIC_EXCHANGE_NAME, "*." + type.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.eventListeners.get(type).add(listener);
		//logger.info("added listener '{}' for event type '{}'.", listener.toString(), type);
	}
	// 取消一个订阅者
	@Override
	public void removeSubscribeMessage(String type, IMessageProcess listener) {
		if (this.eventListeners.containsKey(type)) {
			if (this.eventListeners.get(type).contains(listener))
				this.eventListeners.get(type).remove(listener);
			if (this.eventListeners.get(type).isEmpty())
				this.eventListeners.remove(type);
			if (!this.eventListeners.containsKey(type)) {
				try {
					channel.queueUnbind(topicQueueName, TOPIC_EXCHANGE_NAME, "*." + type.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public byte[] objTobytes(Object obj) throws IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
		objStream.writeObject(obj);
		return byteStream.toByteArray();
	}

	public Object bytesToObj(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
		ObjectInputStream objStream = new ObjectInputStream(byteStream);
		return objStream.readObject();
	}
	
	// 发布一个的消息
	@Override
	public void publishMessage(Message message) {
		BasicProperties props = new BasicProperties.Builder().contentType("EVENT").build();
		try {
			channel.basicPublish(TOPIC_EXCHANGE_NAME, TOPIC_HEADER + message.channel.toString(), props, objTobytes(message));
			//logger.info("add a event:" + message.channel);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void addBroadcastEvent(String type) {
		BasicProperties props = new BasicProperties.Builder().contentType("BROADCAST_EVENT").build();
		try {
			channel.basicPublish(FANOUT_EXCHANGE_NAME, TOPIC_HEADER + type.toString(), props, objTobytes(type));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	private void processEvent(Message message) {
		String type = message.getChannel();
		if (!this.eventListeners.containsKey(type))
			return;
		Set<IMessageProcess> listeners = this.eventListeners.get(type);
		for (IMessageProcess listener : listeners) {
			listener.messageProcess(message);
		}
	}
	public static void main(String[] args) {


	}

	
	
}
