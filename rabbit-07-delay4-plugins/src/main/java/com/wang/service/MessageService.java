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
    public void sendMsg() {
        {
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader("x-delay",15000);//第一条消息延迟时间
            Message message = MessageBuilder.withBody("唐三1".getBytes()).andProperties(messageProperties).build();
            rabbitTemplate.convertAndSend("exchange.delay.4", "plugins", message);
            log.info("发送完毕 发送时间为{}", new Date());
        }
        {
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader("x-delay",10000);//第二条消息延迟时间
            Message message = MessageBuilder.withBody("唐三2".getBytes()).andProperties(messageProperties).build();
            rabbitTemplate.convertAndSend("exchange.delay.4", "plugins", message);
            log.info("发送完毕 发送时间为{}", new Date());
        }
    }
}
