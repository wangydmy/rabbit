package com.wang.config;

import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "my")
@Setter
public class RabbitConfig {
    private String exchangeNormalName;
    private String exchangeAlternateName;
    private String queueNormalName;
    private String queueAlternateName;

    //正常交换机创建与绑定
    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder
                .directExchange(exchangeNormalName)//交换机名字
                .alternate(exchangeAlternateName)//设置备用交换机
                .build();
    }
    @Bean
    public Queue queueNormal() {
        return QueueBuilder.durable(queueNormalName).build();
    }
    @Bean
    public Binding binding(DirectExchange directExchange, Queue queueNormal) {
        return BindingBuilder.bind(queueNormal).to(directExchange).with("info");
    }

    //备用交换机创建与绑定
    @Bean
    public FanoutExchange alternateExchange(){
        return ExchangeBuilder.fanoutExchange(exchangeAlternateName).build();
    }
    @Bean
    public Queue alternateQueue(){
        return QueueBuilder.durable(queueAlternateName).build();
    }
    //扇形交换机不需要路由 key
    @Bean
    public Binding bindingAlternate(FanoutExchange alternateExchange,Queue alternateQueue){
        return BindingBuilder.bind(alternateQueue).to(alternateExchange);
    }
}
