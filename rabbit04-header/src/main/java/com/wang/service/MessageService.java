package com.wang.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class MessageService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        //消息属性
        MessageProperties messageProperties = new org.springframework.amqp.core.MessageProperties();
        Map<String, Object> headers = new HashMap<>();
        headers.put("type","s");
        headers.put("status",0);
        //设置消息头
        messageProperties.setHeaders(headers);
        //添加消息属性
        Message message = MessageBuilder.withBody("hello".getBytes()).
                andProperties(messageProperties).build();
        //头部交换机 路由key无所谓
        rabbitTemplate.convertAndSend("exchange.headers","",message);
        log.info("发送完毕");
    }
}
