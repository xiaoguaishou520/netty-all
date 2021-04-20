package com.cw.controller;

import com.cw.netty.Person;
import com.cw.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author 小怪兽
 * @Date 2021-03-26
 */
@RestController
public class WebSocketController {


    @GetMapping("/mytest")
    public void mytest() throws IOException {
        WebSocketServer.broadcast(new Person("xiaoguaishou",18).toString());
    }
}
