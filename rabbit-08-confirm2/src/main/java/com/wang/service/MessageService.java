package com.wang.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
@Slf4j
public class MessageService implements RabbitTemplate.ConfirmCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
       log.info("关联id{}",correlationData.getId());
        if (ack) {
            log.info("消息正确到达交换机");
        } else {
            log.error("消息没有到达交换机，原因为{}", cause);
        }
    }

    @PostConstruct  // 构造方法执行后 就会执行  相当于初始化
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMsg() {
        Message message = MessageBuilder.withBody("唐三".getBytes()).build();
        CorrelationData correlationData = new CorrelationData();// 关联数据
        correlationData.setId("order_12345"); // 发送订单消息
        rabbitTemplate.convertAndSend("exchange.confirm.b", "info", message, correlationData);
        log.info("发送完毕 发送时间为{}", new Date());
    }
}
