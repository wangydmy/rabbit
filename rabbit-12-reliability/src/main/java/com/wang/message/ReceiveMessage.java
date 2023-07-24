package com.wang.message;


import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j

public class ReceiveMessage {

    @RabbitListener(queues = {"queue.reliability"})
    public void getMsg(Message message, Channel channel) throws IOException {
        //获取消息的唯一标识
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("接收到的消息为{}",new String(message.getBody()));
            //手动确认
            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            log.error("消息处理出现问题");
            channel.basicNack(deliveryTag,false,true);
            throw new RuntimeException(e);
        }
    }


}
