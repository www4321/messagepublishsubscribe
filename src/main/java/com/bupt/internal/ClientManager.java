package com.bupt.internal;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

@Component
public class ClientManager implements IClientManager,Runnable{
	
	private int nettyPort;
	protected Logger logger = LoggerFactory.getLogger(ClientManager.class);	
	@Autowired
	protected ConnectServerHandler connectServerHandler;
	
	public ClientManager(){
		nettyPort = 8000;
		logger.info("ClientManager has benn start.");	
	}
	
	@PostConstruct
	public void postConstruct(){
		new Thread(this).start();
	}
	
	
	@Override
	public List<Channel> getClientEntityByType(String type) {
		List<ClientEntity> clients = connectServerHandler.getClients();
		List<Channel> channels = new ArrayList<Channel>();
		for(ClientEntity client : clients){
			if(client.getType().equals(type))
				channels.add(client.getChannel());
		}
		return channels;
	}

	@Override
	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(); 
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); 
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) 
             .childHandler(new ChannelInitializer<SocketChannel>() { 
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())),
                    		 new ObjectEncoder(),connectServerHandler);
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)          
             .childOption(ChannelOption.SO_KEEPALIVE, true); 
    
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(nettyPort).sync();
    

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
	}

}
