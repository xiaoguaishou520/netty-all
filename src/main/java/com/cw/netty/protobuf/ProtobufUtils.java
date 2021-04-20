package com.cw.netty.protobuf;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author 小怪兽
 * @Date 2021-03-24
 */
@Slf4j
public class ProtobufUtils {

    private static Map<Class<?>, Schema<?>> schemaMap = new ConcurrentHashMap<>();

    /**
     * 序列化:将Java对象转换为字节数组
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> byte[] serializer(T obj){
        Class<T> clazz = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(clazz);
            return ProtostuffIOUtil.toByteArray(obj,schema,buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(),e);
        }
    }

    /**
     * 反序列化:将字节数组转换为Java对象
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deserializer(byte[] data,Class<T> clazz){
        try {
            T obj = clazz.newInstance();
            Schema<T> schema = getSchema(clazz);
            ProtostuffIOUtil.mergeFrom(data,obj,schema);
            return obj;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(),e);
        }
    }

    private static <T> Schema<T> getSchema(Class<T> clazz) {
        Schema<T> schema = (Schema<T>) schemaMap.get(clazz);
        if (schema == null){
            schema = RuntimeSchema.getSchema(clazz);
            log.info("create schema:{}",schema);
            if (schema != null){
                schemaMap.put(clazz,schema);
            }
        }
        return schema;
    }
}
