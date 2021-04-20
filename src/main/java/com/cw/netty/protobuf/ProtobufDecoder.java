package com.cw.netty.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author 小怪兽
 * @Date 2021-03-24
 */
public class ProtobufDecoder extends ByteToMessageDecoder {

    private Class target;

    public ProtobufDecoder(Class target) {
        this.target = target;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        Object deserializer = ProtobufUtils.deserializer(bytes, target);
        out.add(deserializer);
    }
}
