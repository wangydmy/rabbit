package com.wang.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "my")
@Setter
@Slf4j
public class MessageService {
    private String exchangeNormalName;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
            String str = "hello";
            Message message = MessageBuilder.withBody(str.getBytes()).build();
            rabbitTemplate.convertAndSend(exchangeNormalName, "order", message);
        log.info("消息发送完毕");
    }
}
