package com.wang;


import com.wang.service.MessageService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class Main implements ApplicationRunner {
    @Resource
    private MessageService messageService;

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

    //程序启动就会运行
    @Override
    public void run(ApplicationArguments args) throws Exception {
        messageService.sendMsg();
    }
}