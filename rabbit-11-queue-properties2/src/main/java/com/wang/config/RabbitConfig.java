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
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchangeName)// 交换机名字
                .build();
    }

    @Bean
    public Queue queue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-overflow","reject-publish");//设置溢出队列的行为  默认删除队列头部
        arguments.put("x-max-length" ,5); //设置队列最大长度
        return new Queue(queueName, true, false, false,arguments);
    }

    @Bean
    public Binding binding(DirectExchange directExchange, Queue queue) {
        return BindingBuilder.bind(queue).to(directExchange).with("info");
    }
}
