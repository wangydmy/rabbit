package com.wang.config;

import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.Backoff;

@Configuration
@ConfigurationProperties(prefix = "my")
@Setter
public class RabbitConfig {
    private String exchangeName;
    private String queueName;
    @Bean
    public DirectExchange directExchange(){

        //交换机默认持久化
        return ExchangeBuilder.directExchange(exchangeName).build();
    }
    @Bean
    public Queue queue(){
        // durable 持久化
        return QueueBuilder.durable(queueName).build();
    }
    @Bean
    public Binding binding(DirectExchange directExchange,Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with("info");
    }
}
