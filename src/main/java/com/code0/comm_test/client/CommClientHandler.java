package com.code0.comm_test.client;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;


/**  
 * @Title: CommClientHandler.java
 * @Package com.yhwt.nighthawk.comm.client
 * @Description: client 自定义业务处理器
 * @author ZhengMaoDe   
 * @date 2019年3月22日 下午2:51:42 
 */
@Sharable
@Slf4j
public class CommClientHandler extends BaseClientHandler {
	

	
	/**
	 * 
	 * @param ctx
	 * @param s
	 * @see com.yhwt.nighthawk.comm.client.BaseClientHandler#businessHandler(io.netty.channel.ChannelHandlerContext, java.lang.String)
	 */
	@Override
	protected void businessHandler(ChannelHandlerContext ctx, String s) {
		log.debug("我是客户端，我接收到的消息为："+s);
	}

}
