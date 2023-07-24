package com.wang.message;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Slf4j

public class ReceiveMessage {

    @RabbitListener(queues = {"queue.properties.a"})
    public void getMsg(Message message){
        byte[] body = message.getBody();
        String s = new String(body);
        log.info("接收到的消息为{}",s);
    }
}
