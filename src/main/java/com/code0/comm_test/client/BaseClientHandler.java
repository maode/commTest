package com.code0.comm_test.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**  
 * @Title: BaseClientHandler.java
 * @Package com.yhwt.nighthawk.comm.client
 * @Description: 客户端业务处理器基类
 * @author ZhengMaoDe   
 * @date 2019年3月22日 下午2:48:31 
 */
@Slf4j
public abstract class BaseClientHandler  extends SimpleChannelInboundHandler<String> {
	
	/**
	 * 需要实现的处理业务逻辑的方法
	 */
	protected abstract void businessHandler(ChannelHandlerContext ctx, String s);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
		log.trace("我是BaseClientHandler的channelRead0，我默认会调用businessHandler方法");
		businessHandler(ctx, s);
	}
	
    /**
     * 默认心跳处理
     * <p>读超时默认调用handleReaderIdle
     * <br/>写超时默认调用handleWriterIdle
     * <br/>读/写超时默认调用handleAllIdle
     * @param ctx
     * @param evt
     * @throws Exception
     * @see io.netty.channel.ChannelInboundHandlerAdapter#userEventTriggered(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    handleReaderIdle(ctx);
                    break;
                case WRITER_IDLE:
                    handleWriterIdle(ctx);
                    break;
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }

	/**
	 * 处理指定的时间间隔内没有读到数据的空实现-如有需要请覆写
	 * 
	 * @param ctx
	 */
	protected void handleReaderIdle(ChannelHandlerContext ctx) {
		log.trace("---READER_IDLE---指定的时间间隔内没有读到数据");
	}

	/**
	 * 处理指定的时间间隔内没有写入数据的空实现-如有需要请覆写
	 * 
	 * @param ctx
	 */
	protected void handleWriterIdle(ChannelHandlerContext ctx) {
		log.trace("---WRITER_IDLE---指定的时间间隔内没有写入数据");
	}

	/**
	 * 处理指定的时间间隔内没有读/写入数据的空实现--如有需要请覆写
	 * 
	 * @param ctx
	 */
	protected void handleAllIdle(ChannelHandlerContext ctx) {
		log.trace("---ALL_IDLE---指定的时间间隔内没有读/写数据");
	}

	/**
	 * 链接成功建立时触发的函数--如有需要请覆写
	 * 
	 * @param ctx
	 * @throws Exception
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		log.debug("client:" + incoming.remoteAddress() + "成功建立链接");
	}

	/**
	 * 链接断开时触发的函数--如有需要请覆写
	 * 
	 * @param ctx
	 * @throws Exception
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelInactive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		log.debug("client:" + incoming.remoteAddress() + "断开连接");
	}

}