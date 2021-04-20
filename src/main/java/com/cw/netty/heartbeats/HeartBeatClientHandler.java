package com.cw.netty.heartbeats;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @Author 小怪兽
 * @Date 2021-03-25
 */
@Slf4j
public class HeartBeatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("连接服务器成功。。。");
        String msg = "heartbeat\n";
        //采用随机策略来模拟网络问题
        Random random = new Random();
        while (channel.isActive()) {
            int num = random.nextInt(6);
            Thread.sleep(num * 1000);
            channel.writeAndFlush(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("与服务端断开连接");
        NettyClient.reconnect();
    }
}
