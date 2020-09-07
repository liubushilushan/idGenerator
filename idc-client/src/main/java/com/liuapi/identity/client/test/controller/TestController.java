package com.liuapi.identity.client.test.controller;

import com.liuapi.identity.client.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/9/1
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UserService userService;

    private ExecutorService workers = Executors.newFixedThreadPool(20);

    @GetMapping("/strategy1/user/{number}")
    public String createUsers(@PathVariable("number") int number) {
        while (number-- > 0) {
            /**
             * 模仿多个客户端调用该接口
             */
            workers.submit(userService::testCreateStrategy1);
        }
        return "ok";
    }

    @GetMapping("/strategy2/user/{number}")
    public String createUser(@PathVariable("number") int number) {
        while (number-- > 0) {
            /**
             * 模仿多个客户端调用该接口
             */
            workers.submit(userService::testCreateStrategy2);
        }
        return "ok";
    }
}
