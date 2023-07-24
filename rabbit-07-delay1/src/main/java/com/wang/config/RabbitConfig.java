package com.wang.config;

import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.Backoff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "my")
@Setter
public class RabbitConfig {
    private String exchangeName;
    private String queueNormalName;
    private String queueDlxName;
    @Bean
    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange(exchangeName).build();
    }
    @Bean
    public Queue queueNormal(){
        // 设置队列属性
      /*
        方式 1 new Queue

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 25000); // 队列过期时间
        arguments.put("x-dead-letter-exchange",exchangeName);//设置死信交换机
        arguments.put("x-dead-letter-routing-key","error");//设置死信路由
        return new Queue(queueNormalName, true, false, false, arguments);

        */

        //  方式 2 建造者
         return QueueBuilder
                 .durable(queueNormalName)//设置队列名称
                 .ttl(25000)//设置过期时间
                 .deadLetterExchange(exchangeName)//设置死信交换机  和正常交换机一样
                 .deadLetterRoutingKey("error")//设置死信路由
                 .build();
    }

    @Bean
    public Binding bindingNormal(DirectExchange directExchange,Queue queueNormal){
        return BindingBuilder.bind(queueNormal).to(directExchange).with("info");
    }

    @Bean
    public Queue dlxQueue(){
        return QueueBuilder.durable(queueDlxName).build();
    }
    @Bean
    public Binding bindingDlx(DirectExchange directExchange,Queue dlxQueue){
        return BindingBuilder.bind(dlxQueue).to(directExchange).with("error");

    }
}
