package com.wang.message;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class ReceiveMessage {

    //延迟队列一定要接收死信队列消息
    @RabbitListener(queues =  "queue.delay.dlx.3")
    public void getMessage(Message message){
        String s = new String(message.getBody());
        log.info("接收到的消息为：{},接收时间为：{}",s,new Date());
    }
}
