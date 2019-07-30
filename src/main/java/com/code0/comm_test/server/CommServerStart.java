package com.code0.comm_test.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code0.comm_test.Utils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**  
 * @Title: CommServer.java
 * @Package com.yhwt.nighthawk.comm
 * @Description: 通信server入口类
 * @author ZhengMaoDe   
 * @date 2019年3月22日 下午12:51:27 
 */
public class CommServerStart {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final EventLoopGroup bossGroup = new NioEventLoopGroup();
	private final EventLoopGroup workGroup = new NioEventLoopGroup();
	private Channel serverChannel;
	private static final int DEFAULT_PORT=1313;
	
	public void start(int port) {
		if(port<=1024) {
			port=DEFAULT_PORT;
		}
		try{
			ServerBootstrap bootstrap = new ServerBootstrap();//启动 NIO 服务的辅助启动类
			bootstrap.group(bossGroup, workGroup)
					.channel(NioServerSocketChannel.class)//IO操作的多线程事件循环器
					.childHandler(new CommServerInitializer())
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			logger.info("netty服务器在[{}]端口启动监听", port);
			Utils.getServerUI().displayAndWriter("Server服务器在["+port+"]端口启动监听");
			// 绑定端口，开始接收进来的连接
			ChannelFuture future = bootstrap.bind(port).syncUninterruptibly();//绑定当前机器所有网卡的port端口
			serverChannel = future.channel();
			logger.info("注册优雅关闭 serverChannel.closeFuture().sync()");
			serverChannel.closeFuture().sync();
			logger.info("已经开始关闭 serverChannel.closeFuture().sync()");
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("netty服务器channel关闭异常:{}",e.getMessage());
		}finally {
			workGroup.shutdownGracefully();
			logger.info("执行了 workGroup.shutdownGracefully()");
			bossGroup.shutdownGracefully();
			logger.info("执行了 bossGroup.shutdownGracefully()");
			logger.info("netty服务器关闭了~~");
		}
	}
	
	public void destroy() {
		if(serverChannel != null) {
			serverChannel.close();
			serverChannel =null;
		}
	}
	
	public static void main(String[] args) {
		CommServerStart server = new CommServerStart();
		server.start(1313);
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				server.destroy();
			}
		});
		
	}
}
