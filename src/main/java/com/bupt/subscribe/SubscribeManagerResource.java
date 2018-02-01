package com.bupt.subscribe;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import com.bupt.core.IMQMessageManager;
import com.bupt.core.IMessageProcess;
import com.bupt.core.Message;
import com.bupt.internal.IClientManager;

import io.netty.channel.Channel;




@RestController
public class SubscribeManagerResource implements IMessageProcess{

	protected Logger logger = LoggerFactory.getLogger(SubscribeManagerResource.class);
	@Autowired
	protected IMQMessageManager messageManager;
	@Autowired
	private IClientManager clientManager;

	@RequestMapping(value = "/security", method= RequestMethod.POST)
	public String subscribe(@RequestBody SubscribeRequest subscribeRequest){
		logger.info("subscribe channel:" + subscribeRequest.getBody().get("channel"));
		
		String messagetype = subscribeRequest.getBody().get("channel");
		messageManager.subscribeMessage(messagetype, this);
		
		SubscribeResponse subscribeResponse = new SubscribeResponse("ok",subscribeRequest.getId());
		
		return subscribeResponse.toJsonString();
	}
	
	
	@Override
	public void messageProcess(Message message) {
		
		List<Channel> channels = clientManager.getClientEntityByType(message.channel);
		
		if(channels!=null && channels.size()!=0){
			for(Channel channel : channels){
				channel.writeAndFlush(message);
				//用于实验测试
				//channel.close();
			}
		}
	}

}
