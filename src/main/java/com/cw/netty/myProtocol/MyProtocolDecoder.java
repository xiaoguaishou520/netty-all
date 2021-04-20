package com.cw.netty.myProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author 小怪兽
 * @Date 2021-03-25
 */
@Slf4j
public class MyProtocolDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("调用了MyProtocolDecoder自定义解码器。。。");
        //按照协议的规则进行解析
        //协议头用4个字节来表示消息体的大小
        int readableBytes = in.readableBytes();
        if (readableBytes >= 4) {
            //记录初始的读指针位置
            in.markReaderIndex();
            log.info(String.valueOf(in));
            int length = in.readInt();
            log.info(String.valueOf(in));
            if (readableBytes < length) {
                log.info("数据包传输不完整。。。");
                //重置指针位置
                in.resetReaderIndex();
                return;
            }
            //正常情况
            byte[] bytes = new byte[length];
            in.readBytes(bytes);
            //封装对象
            MyProtocol myProtocol = new MyProtocol();
            myProtocol.setLength(length);
            myProtocol.setContent(bytes);
            out.add(myProtocol);
        }
    }
}
