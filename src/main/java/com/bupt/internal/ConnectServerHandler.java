package com.bupt.internal;




import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

@io.netty.channel.ChannelHandler.Sharable
@Component
public class ConnectServerHandler extends ChannelInboundHandlerAdapter {
	
	private List<ClientEntity> clients;
	
	protected Logger logger = LoggerFactory.getLogger(ConnectServerHandler.class);
	
	public ConnectServerHandler(){
		clients = new CopyOnWriteArrayList<ClientEntity>();
	}

	public List<ClientEntity> getClients() {
		return clients;
	}


	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
		for(ClientEntity client : clients){
			if(client.getChannel()==ctx.channel()){
				logger.info("a client<id={}, ip={}>is disconnected.",client.getClientId(), client.getChannel().localAddress().toString());
				clients.remove(client);
			}
				
		}
		
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			
			ConnectEcho echo = (ConnectEcho)msg;
			
			// client id , client channel , the type of message the client subscribes.
			
			clients.add(new ClientEntity(echo.getChannelId(), ctx.channel(), echo.getChannel()));

			
	        
	    } finally {
	        ReferenceCountUtil.release(msg);
	    }
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();  
        ctx.close();
	}

}
