package com.wang.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Slf4j
@ConfigurationProperties(prefix = "my")
@Setter
public class RabbitConfig {
    private String exchangeName;
    private String queueAName;
    private String queueBName;
    //创建交换机
    @Bean
    public TopicExchange topicExchange(){
        return ExchangeBuilder.topicExchange(exchangeName).build();
    }
    //创建消息队列
    @Bean
    public Queue queueA(){
        return QueueBuilder.durable(queueAName).build();
    }
    @Bean
    public Queue queueB(){
        return QueueBuilder.durable(queueBName).build();
    }
    //交换机和队列进行绑定
    @Bean
    public Binding bindingA(TopicExchange topicExchange,Queue queueA){
        return BindingBuilder.bind(queueA).to(topicExchange).with("*.orange.*");
    }
    @Bean
    public Binding bindingB1(TopicExchange topicExchange,Queue queueB){
        return BindingBuilder.bind(queueB).to(topicExchange).with("*.*.rabbit");
    }
    @Bean
    public Binding bindingB2(TopicExchange topicExchange,Queue queueB){
        return BindingBuilder.bind(queueB).to(topicExchange).with("lazy.#");
    }
}
