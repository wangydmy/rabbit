package com.wang.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyReturnCallBack implements RabbitTemplate.ReturnsCallback{
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {

        log.error("消息从交换机没有正确路由到队列，原因为{}",returnedMessage.getReplyText());
    }
}
