/**
 * Copyright (C), 2015-2020
 */
package com.zxb.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建三个队列 ：fanoutA   fanoutB  fanoutC
 * 将三个队列都绑定在交换机 fanoutExchange 上
 * 因为是扇型交换机, 路由键无需配置,配置也不起作用
 *
 * @author zhaoxb
 * @create 2020-04-24 13:46
 */
@Configuration
public class FanoutRabbitConfig {

    @Bean
    public Queue queueA() {
        return new Queue("fanoutA", true, false, false);
    }

    @Bean
    public Queue queueB() {
        return new Queue("fanoutB", true, false, false);
    }

    @Bean
    public Queue queueC() {
        return new Queue("fanoutC", true, false, false);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange", true, false);
    }

    @Bean
    public Binding bindingExchangeA() {
        return BindingBuilder.bind(queueA()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingExchangeB() {
        return BindingBuilder.bind(queueB()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingExchangeC() {
        return BindingBuilder.bind(queueC()).to(fanoutExchange());
    }

}