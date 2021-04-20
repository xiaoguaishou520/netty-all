package com.cw.bio.more_thread;


import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Author 小怪兽
 * @Date 2021-03-22
 */
@Slf4j
public class BioClient {

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            //1.通过三次握手与服务器建立连接（TCP）
            socket = new Socket("localhost",9999);
            log.info("已经与服务器建立好了连接。。。");
            //2.基于IO进行数据的交互
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(),true);
            writer.println("hello,server!!!");
            log.info("接收到服务器的消息：{}",reader.readLine());
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
        }
    }
}
