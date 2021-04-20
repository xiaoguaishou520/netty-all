package com.cw.netty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author 小怪兽
 * @Date 2021-03-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {

    private String name;
    private int age;
}
