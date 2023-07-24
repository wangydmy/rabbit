package com.wang.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
@Slf4j
public class MessageService implements RabbitTemplate.ReturnsCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {

        log.error("消息从交换机没有正确路由到队列，原因为{}", returnedMessage.getReplyText());
    }

    @PostConstruct
    public void init() {
        rabbitTemplate.setReturnsCallback(this);// 设置回调
    }

    public void sendMsg() {
        Message message = MessageBuilder.withBody("唐三".getBytes()).build();
        rabbitTemplate.convertAndSend("exchange.return.b", "info", message);
        log.info("发送完毕 发送时间为{}", new Date());
    }
}
