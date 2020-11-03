package com.fb.im.connect;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author: pangminpeng
 * @create: 2020-09-05 15:33
 */
public class BaseChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //给pipeline添加基本的handler

    }

    protected void addBaseHandler(ChannelPipeline pipeline) {
        //添加超时的handler
        //添加消息处理的handler
        //添加编解码handler
    }
}
