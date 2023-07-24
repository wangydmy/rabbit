package com.wang.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class MessageService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void sendMsg(){
        Message message = MessageBuilder.withBody("唐三".getBytes()).build();
        rabbitTemplate.convertAndSend("exchange.ttl.b","info",message);
        log.info("发送完毕 发送时间为{}",new Date());


    }
}
