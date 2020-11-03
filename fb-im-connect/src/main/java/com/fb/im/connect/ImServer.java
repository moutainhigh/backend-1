package com.fb.im.connect;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author: pangminpeng
 * @create: 2020-08-30 11:20
 */
public class ImServer {


    public void initialize() {
        EventLoopGroup bossGroup;
        EventLoopGroup workerGroup;
        Class<? extends ServerSocketChannel> serverSocketChannelClass;

        // 使用Netty提供的方法判断当前系统是否支持epoll
        if (Epoll.isAvailable()) {
            bossGroup = new EpollEventLoopGroup(4);
            workerGroup = new EpollEventLoopGroup(8);
            serverSocketChannelClass = EpollServerSocketChannel.class;
        } else {
            bossGroup = new NioEventLoopGroup(4);
            workerGroup = new NioEventLoopGroup(8);
            serverSocketChannelClass = NioServerSocketChannel.class;
        }

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup).channel(serverSocketChannelClass)
                .childHandler(new BaseChannelInitializer())
    }


    
}
