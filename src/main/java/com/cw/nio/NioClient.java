package com.cw.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @Author 小怪兽
 * @Date 2021-03-23
 */
@Slf4j
public class NioClient {

    public static void main(String[] args) throws IOException {
        //创建SocketChannel对象
        SocketChannel socketChannel = SocketChannel.open();
        //设置为非阻塞
        socketChannel.configureBlocking(false);
        if (!socketChannel.connect(new InetSocketAddress(9999))) {
            while (!socketChannel.finishConnect()) {
                log.info("正在连接服务器。。。。");
            }
        }
        log.info("连接服务器成功！！！");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String content = scanner.nextLine();
            ByteBuffer byteBuffer = ByteBuffer.wrap(content.getBytes());
            socketChannel.write(byteBuffer);
        }
    }
}
