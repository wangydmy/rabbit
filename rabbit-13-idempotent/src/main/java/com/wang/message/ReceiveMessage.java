package com.wang.message;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.wang.vo.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
@Slf4j
public class ReceiveMessage {
    //反序列化
    @Resource
    private ObjectMapper objectMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @RabbitListener(queues = {"queue.idempotent"})
    public void getMsg(Message message, Channel channel) throws IOException {
        //获取消息的唯一标识
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        Orders orders = objectMapper.readValue(message.getBody(), Orders.class);
        try {
            log.info("接收到的消息为{}",orders.toString());
            //如果不存在 就在redis种存储
            Boolean setResult = stringRedisTemplate.opsForValue().setIfAbsent("idempotent:" + orders.getOrderId(), orders.getOrderId());
            if (setResult){
                //TODO 向数据库插入订单
                log.info("向数据库插入订单");
            }
            //手动确认
            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            log.error("消息处理出现问题");
            channel.basicNack(deliveryTag,false,true);
            throw new RuntimeException(e);
        }
    }

}
