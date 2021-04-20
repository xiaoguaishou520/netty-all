package com.cw.netty.myProtocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 小怪兽
 * @Date 2021-03-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyProtocol {
    /**
     * 定义消息体的长度
     */
    private int length;

    /**
     * 定义消息体的内容
     */
    private byte[] content;

}
