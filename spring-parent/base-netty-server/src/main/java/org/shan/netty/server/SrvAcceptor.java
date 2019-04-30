package org.shan.netty.server;

import java.net.SocketAddress;

/**
 * netty server端的标准接口定义NettySrvAcceptor
 */
public interface SrvAcceptor {
	
	SocketAddress localAddress();
	
	void start() throws InterruptedException;

	void start(boolean sync) throws InterruptedException;
	
	void shutdownGracefully();
	
}
