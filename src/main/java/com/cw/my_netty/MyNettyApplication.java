package com.cw.my_netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cw")
public class MyNettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyNettyApplication.class, args);
    }

}
