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

    public void sendMsg(){
        //使用建造者模式创建消息
        Message message = MessageBuilder.withBody("hello mq".getBytes()).build();
        rabbitTemplate.convertAndSend("exchange.direct","info",message);
        log.info("发送完成");
    }
}
