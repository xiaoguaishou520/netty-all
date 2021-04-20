package com.cw.netty.myProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 小怪兽
 * @Date 2021-03-25
 */
@Slf4j
public class MyProtocolEncoder extends MessageToByteEncoder<MyProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MyProtocol msg, ByteBuf out) throws Exception {
        log.info("调用了MyProtocolEncoder自定义编码器。。。");
        //约定协议
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
