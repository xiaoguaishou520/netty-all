package com.cw.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * @Author 小怪兽
 * @Date 2021-03-23
 */
@Slf4j
public class NettyChatClient {

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
                        //添加netty提供的编解码器
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new NettyChatClientHandler());
                    }
                });

        //4.连接服务器
        try {
            ChannelFuture future = bootstrap.connect("localhost", 9999).sync();
            Channel channel = future.channel();
            log.info("{},连接服务器成功。。。",channel.localAddress());
            //编写给服务端发送消息的逻辑
            Scanner scanner = new Scanner(System.in);
            log.info("现在可以给服务端发送消息");
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅关闭
            group.shutdownGracefully();
        }
    }
}
