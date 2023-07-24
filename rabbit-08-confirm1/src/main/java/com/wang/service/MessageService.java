package com.wang.service;

import com.wang.config.MyConfirmCallBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
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
    @Autowired
    private MyConfirmCallBack myConfirmCallBack;

    @PostConstruct  // 构造方法执行后 就会执行  相当于初始化
    public void init() {
        rabbitTemplate.setConfirmCallback(myConfirmCallBack);
    }

    public void sendMsg() {
        Message message = MessageBuilder.withBody("唐三".getBytes()).build();
        CorrelationData correlationData = new CorrelationData();// 关联数据
        correlationData.setId("order_12345"); // 发送订单消息
        rabbitTemplate.convertAndSend("exchange.confirm.a", "info", message, correlationData);
        log.info("发送完毕 发送时间为{}", new Date());
    }
}
