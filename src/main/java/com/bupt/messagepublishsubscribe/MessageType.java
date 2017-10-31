package com.bupt.messagepublishsubscribe;

public enum MessageType {

	/*
	 * ���԰������д�Զ����Լ������� DIRECT("direct"), FANOUT("fanout"),
	 * TOPIC("topic"), HEADERS("headers");
	 * 
	 * private final String type;
	 * 
	 * MessageType(String type) { this.type = type; }
	 * 
	 * public String getType() { return type; }
	 */

}

// �Զ���message���ԣ�rabbitmq�Ĵ���

/*
 * import com.rabbitmq.client.AMQP.BasicProperties; import
 * com.rabbitmq.client.impl.AMQContentHeader;
 * 
 *//**
	 * Constant holder class with useful static instances of
	 * {@link AMQContentHeader}. These are intended for use with
	 * {@link Channel#basicPublish} and other Channel methods.
	 */
/*
 * public class MessageProperties {
 * 
 *//** Empty basic properties, with no fields set */
/*
 * public static final BasicProperties MINIMAL_BASIC = new BasicProperties(null,
 * null, null, null, null, null, null, null, null, null, null, null, null,
 * null);
 *//** Empty basic properties, with only deliveryMode set to 2 (persistent) */
/*
 * public static final BasicProperties MINIMAL_PERSISTENT_BASIC = new
 * BasicProperties(null, null, null, 2, null, null, null, null, null, null,
 * null, null, null, null);
 * 
 *//**
	 * Content-type "application/octet-stream", deliveryMode 1 (nonpersistent),
	 * priority zero
	 */
/*
 * public static final BasicProperties BASIC = new
 * BasicProperties("application/octet-stream", null, null, 1, 0, null, null,
 * null, null, null, null, null, null, null);
 * 
 *//**
	 * Content-type "application/octet-stream", deliveryMode 2 (persistent),
	 * priority zero
	 */
/*
 * public static final BasicProperties PERSISTENT_BASIC = new
 * BasicProperties("application/octet-stream", null, null, 2, 0, null, null,
 * null, null, null, null, null, null, null);
 * 
 *//**
	 * Content-type "text/plain", deliveryMode 1 (nonpersistent), priority zero
	 */
/*
 * public static final BasicProperties TEXT_PLAIN = new
 * BasicProperties("text/plain", null, null, 1, 0, null, null, null, null, null,
 * null, null, null, null);
 * 
 *//** Content-type "text/plain", deliveryMode 2 (persistent), priority zero *//*
																				 * public
																				 * static
																				 * final
																				 * BasicProperties
																				 * PERSISTENT_TEXT_PLAIN
																				 * =
																				 * new
																				 * BasicProperties
																				 * (
																				 * "text/plain",
																				 * null,
																				 * null,
																				 * 2,
																				 * 0,
																				 * null,
																				 * null,
																				 * null,
																				 * null,
																				 * null,
																				 * null,
																				 * null,
																				 * null,
																				 * null
																				 * )
																				 * ;
																				 * }
																				 */