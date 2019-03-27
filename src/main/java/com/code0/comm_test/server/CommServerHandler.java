package com.code0.comm_test.server;


import com.code0.comm_test.Utils;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;


/**  
 * @Title: CommServerHandler.java
 * @Package com.yhwt.nighthawk.comm
 * @Description: 通信server自定义业务处理器
 * @author ZhengMaoDe   
 * @date 2019年3月22日 下午12:50:39 
 */
@Sharable
@Slf4j
public class CommServerHandler extends BaseServerHandler<String> {

	@Override
	protected void businessHandler(ChannelHandlerContext ctx, String s) {
		log.debug("我是服务端，我接收到的消息为："+s);
		Utils.getServerUI().displayAndWriter("服务端接收到的消息为："+s);
		String rmsg=s+"-result";
		log.debug("我是服务端，我准备发送的消息为："+rmsg);
		
		ctx.channel().writeAndFlush(rmsg+"\n");
		Utils.getServerUI().displayAndWriter("服务端发送到客户端的消息为："+rmsg);
	}


}