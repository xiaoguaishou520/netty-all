package com.cw.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 小怪兽
 * @Date 2021-03-23
 */
@Slf4j
public class NettyChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 需要一个集合来管理所有的客户端
     */
    private static ChannelGroup channelGroup =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 客户端上线，触发方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //1.获取当前客户端的Channel
        Channel channel = ctx.channel();
        //2.将当前Channel存入ChannelGroup
        channelGroup.add(channel);
        log.info("{}:上线了。。。",channel.remoteAddress());
        log.info("连接的客户端数量为：{}",channelGroup.size());
        //3.告知其他客户端，新用户上线了
        channelGroup.writeAndFlush("用户：" + channel.remoteAddress() + "上线了");
    }

    /**
     * 客户端下线，触发方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //1.获取当前客户端的Channel
        Channel channel = ctx.channel();
        //2.将当前Channel从ChannelGroup中移除
        channelGroup.remove(channel);
        log.info("{}:下线了",channel.remoteAddress());
        log.info("连接的客户端数量为：{}",channelGroup.size());
        //3.告知其他客户端下线了
        channelGroup.writeAndFlush("用户：" + channel.remoteAddress() + "下线了");
    }

    /**
     * 读事件就绪时，触发方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //获取当前channel
        Channel currentChannel = ctx.channel();
        log.info("{}说：{}",ctx.channel().remoteAddress(),msg);
        //遍历分发
        for (Channel channel : channelGroup) {
            if (channel == currentChannel) {
                //己方回显
                currentChannel.writeAndFlush("我说：" + msg);
            } else {
                //发送给其他客户端
                channel.writeAndFlush("用户：" + currentChannel.remoteAddress() + "说：" + msg);
            }
        }
    }

    /**
     * 捕获异常,关闭上下文
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
        ctx.close();
    }
}
