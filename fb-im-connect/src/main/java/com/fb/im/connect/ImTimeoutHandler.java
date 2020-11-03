package com.fb.im.connect;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author: pangminpeng
 * @create: 2020-09-06 16:54
 */
public class ImTimeoutHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState e = ((IdleStateEvent) evt).state();
            if (IdleState.READER_IDLE == e) {
                //下面的这些执行逻辑和执行顺序为什么是这样的呢
                ctx.fireChannelInactive();
                ctx.channel().close();
                ctx.close();
            }
        }
        super.userEventTriggered(ctx, evt);
    }
}
