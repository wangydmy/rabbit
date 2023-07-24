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
    private String queueDelayName;

    /*
    创建自定义交换机
     */
    @Bean
    public CustomExchange CustomExchange() {

        Map<String, Object> arguments = new HashMap<>();

        arguments.put("x-delayed-type", "direct");// 放参数
        return new CustomExchange(exchangeName, "x-delayed-message", true, false, arguments);
    }

    @Bean
    public Queue queue() {
        //  方式 2 建造者
        return QueueBuilder
                .durable(queueDelayName)// 设置队列名称
                .build();
    }

    @Bean
    public Binding binding(CustomExchange customExchange, Queue queue) {
        //绑定也要指定 路由key
        return BindingBuilder.bind(queue).to(customExchange).with("plugins").noargs();
    }

}
