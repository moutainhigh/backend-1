package com.fb.im.connect;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.List;

/**
 * @author: pangminpeng
 * @create: 2020-09-08 22:29
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast("websocket", new WebSocketServerProtocolHandler("/websocket", "protobuf"));
        pipeline.addLast("websocketDecoder", new MessageToMessageDecoder<BinaryWebSocketFrame>() {

            @Override
            protected void decode(ChannelHandlerContext ctx, BinaryWebSocketFrame msg, List<Object> out) throws Exception {
                ByteBuf byteBuf = msg.content();
                byteBuf.retain();
                out.add(byteBuf);
            }
        });
       
        pipeline.addFirst("idleStateHandler", new IdleStateHandler(Constants.READ_TIME_OUT_SECONDS, 0, 0));
        pipeline.addAfter("idleStateHandler", "idleEventHandler", new ImTimeoutHandler());

    }
}
