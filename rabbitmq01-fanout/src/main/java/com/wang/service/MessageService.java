package com.wang.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class MessageService {
    @Resource

    private RabbitTemplate rabbitTemplate;
    public void sendMsg(){
        String msg="hello mq";
        //将字符串转为字节数组 msg.getBytes()
        Message message = new Message(msg.getBytes());
        //发送消息
        rabbitTemplate.convertAndSend("exchange.fanout","",message);
        log.info("发送完毕");
    }
}
