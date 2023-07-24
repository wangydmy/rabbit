package com.wang;


import com.wang.service.MessageService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import javax.annotation.Resource;

@SpringBootApplication
public class Main implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }

    @Resource
    private MessageService  messageService;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        messageService.sendMsg();
    }
}