package com.wang.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wang.vo.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
public class MessageService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;// 可以进行序列化和反序列化

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
            if (!b) {
                log.info("消息没有成功发到交换机" + s);
            }
        });
        rabbitTemplate.setReturnsCallback(returnedMessage -> log.error("消息发送到队列失败"));
    }

    public void sendMsg() throws JsonProcessingException {

        {
            // 使用建造者模式创建订单
            Orders orders1 = Orders.builder()
                    .orderId("order_12345")
                    .orderName("小米手机")
                    .orderMoney(new BigDecimal(5555))
                    .orderTime(new Date())
                    .build();
            // 将对象转化为json
            String strOrders1 = objectMapper.writeValueAsString(orders1);
            // 设置消息属性
            MessageProperties messageProperties = new MessageProperties();
            // 设置单条消息的持久化   默认持久化
            messageProperties.setReceivedDeliveryMode(MessageDeliveryMode.PERSISTENT);
            Message message = MessageBuilder.withBody(strOrders1.getBytes()).andProperties(messageProperties).build();
            rabbitTemplate.convertAndSend("exchange.idempotent", "info", message);
        }
        {
            Orders orders2 = Orders.builder()
                    .orderId("order_12345")
                    .orderName("小米手机")
                    .orderMoney(new BigDecimal(5555))
                    .orderTime(new Date())
                    .build();
            String strOrders2 = objectMapper.writeValueAsString(orders2);
            //设置消息属性
            MessageProperties messageProperties = new MessageProperties();
            //设置单条消息的持久化   默认持久化
            messageProperties.setReceivedDeliveryMode(MessageDeliveryMode.PERSISTENT);
            Message message = MessageBuilder.withBody(strOrders2.getBytes()).andProperties(messageProperties).build();
            rabbitTemplate.convertAndSend("exchange.idempotent","info",message);
        }

        log.info("发送完毕 发送时间为{}", new Date());


    }
}
