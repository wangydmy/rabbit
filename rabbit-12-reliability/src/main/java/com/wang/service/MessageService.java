package com.wang.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
@Slf4j
public class MessageService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
            if (!b){
                log.info("消息没有成功发到交换机"+s);
            }
        });
        rabbitTemplate.setReturnsCallback(returnedMessage -> log.error("消息发送到队列失败"));
    }
    public void sendMsg(){
        //设置消息属性
        MessageProperties messageProperties = new MessageProperties();
        //设置单条消息的持久化   默认持久化
        messageProperties.setReceivedDeliveryMode(MessageDeliveryMode.PERSISTENT);
        Message message = MessageBuilder.withBody("唐三".getBytes()).andProperties(messageProperties).build();
        rabbitTemplate.convertAndSend("exchange.reliability","info",message);
        log.info("发送完毕 发送时间为{}",new Date());


    }
}
