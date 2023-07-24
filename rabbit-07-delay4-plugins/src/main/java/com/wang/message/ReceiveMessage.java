package com.wang.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class ReceiveMessage {

    //接收延迟队列消息 延迟队列使用的是延迟交换机插件
    @RabbitListener(queues =  "queue.delay.4")
    public void getMessage(Message message){
        String s = new String(message.getBody());
        log.info("接收到的消息为：{},接收时间为：{}",s,new Date());
    }
}
