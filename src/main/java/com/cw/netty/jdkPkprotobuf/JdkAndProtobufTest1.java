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
public class JdkAndProtobufTest1 {

    public static void main(String[] args) throws IOException {
        Person person = new Person("xiaoguaishou",18);
        //1.采用JDK的序列化方式
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);
        objectOutputStream.writeObject(person);
        objectOutputStream.flush();
        objectOutputStream.close();
        byte[] bytes = bos.toByteArray();
        log.info("JDK序列化后码流大小：{}",bytes.length);

        //2.采用Protobuf序列化方式
        byte[] serializer = ProtobufUtils.serializer(person);
        log.info("Protobuf序列化后码流大小：{}",serializer.length);
    }
}
