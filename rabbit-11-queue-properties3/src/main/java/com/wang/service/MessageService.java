package com.wang.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class MessageService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${my.exchangeName}")
    private String exchangeName;

    public void sendMsg() {

        for (int i = 1; i <= 100; i++) {
            String str = "hello" + i;
            Message message = MessageBuilder.withBody(str.getBytes()).build();
            rabbitTemplate.convertAndSend(exchangeName, "info", message);
        }

        log.info("发送完毕 发送时间为{}", new Date());


    }
}
