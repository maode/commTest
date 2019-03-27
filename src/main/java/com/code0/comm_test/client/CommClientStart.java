package com.code0.comm_test.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**  
 * @Title: CommClientStart.java
 * @Package com.yhwt.nighthawk.comm.client
 * @Description: client通信 入口类
 * @author ZhengMaoDe   
 * @date 2019年3月22日 下午2:54:58 
 */
public class CommClientStart {
	private Logger logger=LoggerFactory.getLogger(getClass());

    private NioEventLoopGroup workGroup = new NioEventLoopGroup();
    private Channel channel;
    private Bootstrap bootstrap;
    private final String host;
    private final int port;
    private static final long DEFAULT_REDIRECT_SECOND=10; 


    public CommClientStart(String host, int port){
        this.host = host;
        this.port = port;
    }



    public void start() {
        try {
            bootstrap = new Bootstrap();
            bootstrap
                    .group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new CommClientInitializer());
            doConnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doConnect() {
        if (channel != null && channel.isActive()) {
            return ;
        }

        ChannelFuture future = bootstrap.connect(host, port);

        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (futureListener.isSuccess()) {
                    channel = futureListener.channel();
                } else {
                    logger.debug("链接失败"+DEFAULT_REDIRECT_SECOND+"s后执行重连！");

                    futureListener.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            doConnect();
                        }
                    }, DEFAULT_REDIRECT_SECOND, TimeUnit.SECONDS);
                }
            }
        });
        
    }





	/**
	 * 本地非集成环境测试方法
	 * 
	 * @throws Exception
	 */
	public void test() throws Exception {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			String readLine = in.readLine();
			String msg="";
			if ("1".equals(readLine)) {
				msg="1111111111";
			} else if ("2".equals(readLine)) {
				msg="2222222222";
			} else {
				msg=readLine;
			}
			logger.debug("我是客户端，我准备发送的消息为：" + msg);
			channel.writeAndFlush(msg+"\n");
		}

	}
	
	
    
    
    public static void main(String[] args) throws Exception {
    	CommClientStart client = new CommClientStart("127.0.0.1",1314);
        client.start();
        client.test();
    }

    
	
}









