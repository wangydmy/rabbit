package com.wang.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@Setter
@ConfigurationProperties(prefix = "my")
public class RabbitConfig {
    private String exchangeName;
    private String queueAName;
    private String queueBName;
    //创建交换机
    @Bean
    public HeadersExchange headersExchange(){
        return ExchangeBuilder.headersExchange(exchangeName).build();
    }
    @Bean
    public Queue queueA(){
        return QueueBuilder.durable(queueAName).build();
    }
    @Bean
    public Queue queueB(){
        return QueueBuilder.durable(queueBName).build();
    }
    @Bean
    public Binding bindingA(HeadersExchange headersExchange,Queue queueA){
        Map<String, Object> headerValues = new HashMap<>();
        headerValues.put("type", "m");
        headerValues.put("status", 1);
        return BindingBuilder.bind(queueA).to(headersExchange).whereAll(headerValues).match();
    }
    @Bean
    public Binding bindingB(HeadersExchange headersExchange,Queue queueB){
        Map<String, Object> headerValues = new HashMap<>();
        headerValues.put("type", "s");
        headerValues.put("status", 0);
        return BindingBuilder.bind(queueB).to(headersExchange).whereAll(headerValues).match();
    }
}
