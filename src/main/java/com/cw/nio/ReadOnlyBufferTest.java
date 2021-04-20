package com.cw.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * @Author 小怪兽
 * @Date 2021-03-23
 */
@Slf4j
public class ReadOnlyBufferTest {

    public static void main(String[] args) {
        //创建原始缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 97);
        buffer.put((byte) 98);
        buffer.put((byte) 99);
        log.info("原Buffer：{}",buffer);

        //基于Buffer创建一个只读缓冲区
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        log.info("只读缓冲区：{}",readOnlyBuffer);//修改只读缓冲区的内容会抛出异常

        //修改原缓冲区的内容
        byte b = buffer.get(1);
        b = 100;
        buffer.put(1,b);

        //查看只读缓冲是否有变化
        readOnlyBuffer.flip();
        StringBuffer stringBuffer = new StringBuffer();
        while (readOnlyBuffer.remaining() > 0) {
            stringBuffer.append(String.valueOf(readOnlyBuffer.get()));
        }
        log.info("修改原缓冲区后，只读缓冲区内容：{}",stringBuffer);
    }
}
