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
    public class MessageService  {
        @Autowired
        private RabbitTemplate rabbitTemplate;

        @PostConstruct
        public void init() {
            rabbitTemplate.setReturnsCallback(returnedMessage -> log.error("消息发送失败"));// 设置回调
        }

        public void sendMsg() {
            Message message = MessageBuilder.withBody("唐三".getBytes()).build();
            rabbitTemplate.convertAndSend("exchange.return.c", "info", message);
            log.info("发送完毕 发送时间为{}", new Date());
        }
    }
