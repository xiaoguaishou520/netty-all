package com.cw.netty.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 小怪兽
 * @Date 2021-03-23
 */
@Slf4j
public class NettyClient {

    public static void main(String[] args) {
        //1.创建一个线程池，用于读写交互
        EventLoopGroup group = new NioEventLoopGroup();
        //2.创建一个客户端启动对象
        Bootstrap bootstrap = new Bootstrap();
        //3.设置启动的相关参数
        //设置线程池
        bootstrap.group(group)
                //设置通道为NIO通道
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new NettyClientHandler());
                    }
                });

        //4.连接服务器
        try {
            ChannelFuture future = bootstrap.connect("localhost", 9999).sync();
            log.info("连接服务器成功。。。");
            //对通道关闭进行监听
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅关闭
            group.shutdownGracefully();
        }
    }
}
