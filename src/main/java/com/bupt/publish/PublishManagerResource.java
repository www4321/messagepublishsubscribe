package com.bupt.publish;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bupt.core.IMQMessageManager;
import com.bupt.core.Message;
import com.bupt.elasticsearchclient.IMsgStorageManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@RestController
public class PublishManagerResource {
	@Autowired
	protected IMQMessageManager messageManager;
	@Autowired
	protected IMsgStorageManager msgStorageManager;
	protected Logger logger = LoggerFactory.getLogger(PublishManagerResource.class);
	
	@RequestMapping(value = "/", method= RequestMethod.POST)
	public String publish(@RequestBody PublishRequest publishRequest) throws JsonProcessingException{
		
		String channel = publishRequest.getBody().get("channel");
		String content = publishRequest.getBody().get("message");
		
		Message message = new Message(channel,content);
		ObjectMapper objectMapper = new ObjectMapper(); 
		logger.info("publish message:{channel:" + channel+",id:"+publishRequest.getId()+",message:"+message.getMessage());
		messageManager.publishMessage(message);
		
		//msgStorageManager.index("security", "threat", objectMapper.writeValueAsString(message));
		
		PublishResponse publishResponse = new PublishResponse("ok", publishRequest.getId(),channel);
		
		return publishResponse.toJsonString();
	}

}
