package com.cw.netty.jdkPkprotobuf;

import com.cw.netty.Person;
import com.cw.netty.protobuf.ProtobufUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @Author 小怪兽
 * @Date 2021-03-24
 */
@Slf4j
public class JdkAndProtobufTest2 {

    public static void main(String[] args) throws IOException {
        Person person = new Person("xiaoguaishou",18);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            //1.采用JDK的序列化方式
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);
            objectOutputStream.writeObject(person);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
        long end = System.currentTimeMillis();
        log.info("JDK序列化100万次消耗的时间：{}",end - start);

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            //2.采用Protobuf序列化方式
            byte[] serializer = ProtobufUtils.serializer(person);
        }
        end = System.currentTimeMillis();
        log.info("Protobuf序列化100万次消耗的时间：{}",end - start);
    }
}
