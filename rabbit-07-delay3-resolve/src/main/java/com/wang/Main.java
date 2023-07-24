package com.wang;

import com.wang.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements ApplicationRunner {
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

    @Autowired
    private MessageService messageService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        messageService.sendMsg();
    }
}