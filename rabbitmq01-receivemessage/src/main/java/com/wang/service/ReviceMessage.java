package com.wang.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReviceMessage {

    @RabbitListener(queues={  "queue.topic.a","queue.topic.b"})
    public void getMessage(Message message){
        byte[] body = message.getBody();
        String s = new String(body);
        log.info("接收消息"+s);
    }
}
