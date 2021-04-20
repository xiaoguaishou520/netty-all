package com.cw.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author 小怪兽
 * @Date 2021-03-23
 */
@Slf4j
public class FileOutputTest {

    public static void main(String[] args) throws IOException {
        //从FileOutputStream获取channel
        FileOutputStream outputStream = new FileOutputStream(
                "D:\\maven\\my_project\\my_netty\\src\\main\\java\\com\\cw\\nio\\name.txt"
        );
        FileChannel channel = outputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 97);
        buffer.put((byte) 98);
        buffer.put((byte) 99);
        log.info("写入后：{}",buffer);

        buffer.flip();
        log.info("flip之后：{}",buffer);
        //将Buffer通过Channel写入到文件中
        channel.write(buffer);
        channel.close();
    }
}
