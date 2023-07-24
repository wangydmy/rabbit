package com.wang.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void sendMessage(){
        Message message = MessageBuilder.withBody("hello mq".getBytes()).build();
        rabbitTemplate.convertAndSend("exchange.topic","lazy.orange.rabbit",message);
        log.info("发送完毕");
    }
}
