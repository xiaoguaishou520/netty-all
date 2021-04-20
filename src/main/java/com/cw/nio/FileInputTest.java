package com.cw.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author 小怪兽
 * @Date 2021-03-23
 */
@Slf4j
public class FileInputTest {

    public static void main(String[] args) throws IOException {
        //获取channel
        FileInputStream inputStream = new FileInputStream(
                "D:\\maven\\my_project\\my_netty\\src\\main\\java\\com\\cw\\nio\\name.txt"
        );
        FileChannel channel = inputStream.getChannel();
        //创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(24);
        //将数据从Channel读取到Buffer
        channel.read(buffer);
        log.info("读取的数据：{}",buffer);

        //关键点：将position设置为0，limit设置为原先写到的position的位置
        //[pos=0 lim=12 cap=24]
        buffer.flip();
        log.info("调用flip之后：{}",buffer);

        //读取缓冲区的数据
        StringBuffer stringBuffer = new StringBuffer();
        while (buffer.remaining() > 0) {
            byte b = buffer.get();
            stringBuffer.append(String.valueOf((char) b));
        }
        log.info("buffer的数据：{}",stringBuffer.toString());
        channel.close();
    }
}
