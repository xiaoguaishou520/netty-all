package com.cw.memory;

import java.nio.ByteBuffer;

/**
 * @Author 小怪兽
 * @Date 2021-03-29
 */
public class OutMemory {

    public static void main(String[] args) {
        //分配堆内存
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(1024);

        //分配堆外内存
        ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(1024);

    }
}
