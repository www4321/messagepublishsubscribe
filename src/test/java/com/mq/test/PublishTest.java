package com.mq.test;

import com.bupt.publish.PublishRequest;
import com.bupt.publish.PublishResponse;
import com.bupt.utils.HttpUtils;

public class PublishTest {

	public static void main(String[] args) {
		String url = "http://127.0.0.1:8080/";
		String channel = "security";
		String message = "first publish message";
		
		PublishRequest pulishRequest = new PublishRequest(channel, message);
		System.out.println(pulishRequest.toJsonString());
		String result = HttpUtils.httpPost(url, null, pulishRequest.toJsonString());
		PublishResponse publishResponse = new PublishResponse(result);
		System.out.println(publishResponse.getAction()+":"+publishResponse.getUuid());

	}

}
