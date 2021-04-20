package com.cw.netty.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 小怪兽
 * @Date 2021-03-23
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端连接上服务端，触发方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("新客户端连接上了");
    }

    /**
     * 有数据可读的时候，触发方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //将msg转换为ByteBuf
        ByteBuf buf = (ByteBuf) msg;
        log.info("接受到客户端的消息：{}",buf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 数据读取完毕，触发方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //构建一个ByteBuf，作为传递单位
        ByteBuf buf = Unpooled.copiedBuffer(
                "hello,this is netty server!".getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(buf);

    }


}
