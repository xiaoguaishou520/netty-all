package com.cw.netty.heartbeats;

import com.cw.netty.basic.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author 小怪兽
 * @Date 2021-03-23
 */
@Slf4j
public class NettyClient {

    private static Bootstrap bootstrap;

    public static void main(String[] args) {
        //1.创建一个线程池，用于读写交互
        EventLoopGroup group = new NioEventLoopGroup();
        //2.创建一个客户端启动对象
        bootstrap = new Bootstrap();
        //3.设置启动的相关参数
        //设置线程池
        bootstrap.group(group)
                //设置通道为NIO通道
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new HeartBeatClientHandler());
                    }
                });

        //4.连接服务器
        try {
            reconnect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void reconnect() throws InterruptedException{
        ChannelFuture future = bootstrap.connect("localhost", 9998);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    log.info("连接服务器成功！");
                } else {
                    log.info("连接服务器失败！");
                    future.channel().eventLoop().schedule(()->{
                        log.info("1秒后,自动重连");
                        try {
                            reconnect();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    },1, TimeUnit.SECONDS);
                }
            }
        });

    }
}
