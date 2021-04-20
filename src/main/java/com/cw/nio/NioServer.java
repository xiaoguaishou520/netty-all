package com.cw.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author 小怪兽
 * @Date 2021-03-23
 */
@Slf4j
public class NioServer {

    public static void main(String[] args) throws IOException {
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9999));
        //设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //创建一个Selector对象
        Selector selector = Selector.open();
        //将ServerSocketChannel注册到Selector，声明关注连接事件
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        log.info("服务端在9999端口监听。。。");

        while (true) {
            //判断当前跟Selector绑定的Channel是否有就绪事件
            selector.select();
            //获取到Selector中的就绪列表
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //判断事件的类型
                if (key.isAcceptable()) {
                    //事件为连接事件
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    //通过服务端的channel创建客户端的channel
                    SocketChannel socketChannel = server.accept();
                    //设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //注册读事件
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    log.info("{}客户端连接成功。。。",socketChannel);
                } else if (key.isReadable()) {
                    //事件为读事件
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    //创建Buffer来读取channel过来的数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int read = socketChannel.read(buffer);
                    if (read > 0) {
                        log.info("收到客户端发来的消息：{}",new String(buffer.array()));
                    }
                }
                //删除本次SelectionKey，避免下次重复处理
                iterator.remove();
            }

        }
    }
}
