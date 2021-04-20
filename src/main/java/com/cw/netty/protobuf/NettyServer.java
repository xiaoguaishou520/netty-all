package com.cw.netty.protobuf;

import com.cw.netty.Person;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 小怪兽
 * @Date 2021-03-23
 */
@Slf4j
public class NettyServer {

    public static void main(String[] args) {
        //1.创建两个线程组
        //一个负责处理客户端的连接请求
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //一个负责处理客户端的读写请求
        EventLoopGroup wokerGroup = new NioEventLoopGroup();

        //2.创建一个服务端启动对象
        ServerBootstrap bootstrap = new ServerBootstrap();
        //3.为启动对象设置相关参数
        //设置主从线程模式
        bootstrap.group(bossGroup,wokerGroup)
                //设置通道的类型为NIO类型
                .channel(NioServerSocketChannel.class)
                //设置从线程的处理逻辑
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new ProtobufDecoder(Person.class));
                        pipeline.addLast(new NettyServerHandler());
                    }
                });

        //4.绑定监听端口
        try {
            ChannelFuture future = bootstrap.bind(9999).sync();
            log.info("服务端已启动，在9999端口进行监听。。。");
            //程序监听听NioServerSocketChannel的关闭事件并同步阻塞main函数
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅关闭
            bossGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }
    }
}
