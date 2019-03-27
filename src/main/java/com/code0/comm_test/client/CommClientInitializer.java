package com.code0.comm_test.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

/**  
 * @Title: CommClientInitializer.java
 * @Package com.yhwt.nighthawk.comm.client
 * @Description: client 通信处理器初始化
 * @author ZhengMaoDe   
 * @date 2019年3月22日 下午2:52:57 
 */
public class CommClientInitializer extends ChannelInitializer<SocketChannel> {
	

		@Override
	    public void initChannel(SocketChannel ch) throws Exception {
	        ChannelPipeline pipeline = ch.pipeline();
	        
	        pipeline.addLast(new IdleStateHandler(60, 0, 0));//心跳
	        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()));
	        pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
	        pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
	        pipeline.addLast("handler", new CommClientHandler());
	    }
		
	}