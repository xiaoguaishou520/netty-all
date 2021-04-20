package com.cw.bio.one_thread;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author 小怪兽
 * @Date 2021-03-22
 */
@Slf4j
public class BioServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            //1.服务器开启端口监听客户端的连接请求
            serverSocket = new ServerSocket(9999);
            log.info("服务器开始在9999端口监听。。。");
            //2.接收客户端的连接请求
            while (true) {
                //3.阻塞接收客户端的连接请求
                socket = serverSocket.accept();
                log.info("接收到客户端的连接请求：{}",socket);
                //4.基于IO流进行数据的交互
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(),true);
                log.info("接收到客户端的消息：{}",reader.readLine());
                writer.println("服务端已经接收到你的消息！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

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
