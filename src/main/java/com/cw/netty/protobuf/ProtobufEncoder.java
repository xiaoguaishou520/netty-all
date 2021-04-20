package com.cw.netty.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author 小怪兽
 * 将自定义对象转换为Bytebuf
 * @Date 2021-03-24
 */
public class ProtobufEncoder extends MessageToByteEncoder<Object> {

    private Class clazz;

    public ProtobufEncoder(Class target) {
        this.clazz = target;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        //如果是目标类型就进行编码
        if (msg != null && msg.getClass().equals(clazz)) {
            out.writeBytes(ProtobufUtils.serializer(msg));
        }
    }
}
