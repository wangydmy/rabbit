package com.wang.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReceiveMessage {

    //接收两个队列的消息
    @RabbitListener(queues = {"queue.fanout.a","queue.fanout.b"})
    public void receiveMessage(Message message){
        byte[] body = message.getBody();
        String msg = new String(body);
        log.info("接收到的消息"+msg);
    }
}
