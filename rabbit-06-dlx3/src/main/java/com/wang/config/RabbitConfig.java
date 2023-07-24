package com.wang.config;

import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@ConfigurationProperties(prefix = "my")
@Setter
public class RabbitConfig {
    private String exchangeNormalName;
    private String queueNormalName;
    private String exchangeDlxName;
    private String queueDlxName;
    @Bean
    public DirectExchange normalExchange(){


        return ExchangeBuilder.directExchange(exchangeNormalName).build();
    }
    @Bean
    public Queue normalQueue(){
        //设置队列过期时间
        Map<String, Object> arguments = new HashMap<>();
        //设置队列的最大长度
        arguments.put("x-max-length",5);
        //设置死信交换机 当队列过期后 消息转入死信交换机
        arguments.put("x-dead-letter-exchange",exchangeDlxName);
        //设置死信路由key
        arguments.put("x-dead-letter-routing-key","error");
        return QueueBuilder.durable(queueNormalName).
                withArguments(arguments)//设置队列参数
                .build();
    }
    @Bean
    public Binding normalBinding(DirectExchange normalExchange,Queue normalQueue){
        return BindingBuilder.bind(normalQueue).to(normalExchange).with("order");
    }


    @Bean
    public DirectExchange dlxExchange(){
        return ExchangeBuilder.directExchange(exchangeDlxName).build();
    }
    @Bean
    public Queue dlxQueue(){
        return QueueBuilder.durable(queueDlxName).build();
    }
    @Bean
    public Binding bindingDlx(DirectExchange dlxExchange,Queue dlxQueue){
        return BindingBuilder.bind(dlxQueue).to(dlxExchange).with("error");
    }
}
