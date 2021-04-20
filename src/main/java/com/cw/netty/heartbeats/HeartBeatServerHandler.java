package com.cw.netty.heartbeats;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Author 小怪兽
 * @Date 2021-03-25
 */
@Slf4j
public class HeartBeatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 记录超时次数
     */
    private int timeoutCount = 0;

    /**
     * 最近一次的超时时间点
     */
    private long lastIdleTime = 0;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("{}:上线了",ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("{}:下线了",ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if ("heartbeat".equals(msg)) {
            log.info("收到{}的心跳包：{}", ctx.channel(), new Date());
            if (System.currentTimeMillis() - lastIdleTime >= 4000) {
                log.info("{}已经稳定，超时次数清零", ctx.channel());
                timeoutCount = 0;
            }
        } else {
            log.info("非心跳信息，不做处理...");
        }
    }

    /**
     * 心跳出现超时，触发该方法
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;
        Channel channel = ctx.channel();
        switch (event.state()) {
            case READER_IDLE:
                Date now = new Date();
                log.info("{}:出现了一次心跳超时{}",channel,now);
                timeoutCount ++;
                lastIdleTime = now.getTime();
                break;
            default:
                break;
        }
        if (timeoutCount >= 3) {
            log.info("{}心跳超时达到3次，关闭该连接",channel);
            channel.close();
        }
    }
}
