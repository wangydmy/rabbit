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
    private String queueName;
    @Bean
    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange(exchangeName).build();
    }
    @Bean
    public Queue queue(){
        // 设置队列属性
        // 方式 1 new Queue
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 15000);
        // 队列过期时间
        return new Queue(queueName, true, false, false, arguments);
        //  方式 2 建造者
        // return QueueBuilder.durable(queueName).
        //         withArguments(arguments).build();
    }
    @Bean
    public Binding binding(DirectExchange directExchange,Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with("info");
    }
}
