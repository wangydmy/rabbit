package com.wang.message;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
public class MessageReceive {
    @RabbitListener(queues = {"queue.normal.d"})
    public void getMessage(Message message , Channel channel) throws IOException {
        //获取消息属性
        MessageProperties messageProperties = message.getMessageProperties();
        //获取消息的唯一标志
        long deliveryTag = messageProperties.getDeliveryTag();
        try {
            //将接收到的消息字节数组转化为string类型
            byte[] body = message.getBody();
            String s = new String(body);
            log.info("接收到的消息为"+s);
            //消费者的手动确认 false 只确定一条 改为 true 为批量确认
            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            log.error("接收者出现问题"+e.getMessage());
            // deliveryTag 处理那条消息
            // false 处理一条
            // true 消息重新入队
            //channel.basicNack(deliveryTag,false,true);  //消息不会进入死信队列
            //手动不确认
            channel.basicNack(deliveryTag,false,false);  //消息会进入死信队列
            throw new RuntimeException(e);
        }
    }
}
