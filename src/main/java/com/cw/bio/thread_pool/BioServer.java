package com.cw.bio.thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author 小怪兽
 * @Date 2021-03-22
 */
@Slf4j
public class BioServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,2,0,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(100));
        try {
            //1.服务器开启端口监听客户端的连接请求
            serverSocket = new ServerSocket(9999);
            log.info("服务器开始在9999端口监听。。。");
            //2.接收客户端的连接请求
            while (true) {
                //3.阻塞接收客户端的连接请求
                socket = serverSocket.accept();
                log.info("接收到客户端的连接请求：{}",socket);
                threadPool.execute(new BioServerHandler(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
