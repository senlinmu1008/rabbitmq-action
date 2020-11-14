package com.zxb.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置自定义延迟消息的交换机
 *
 * @author zhaoxb
 * @date 2020/09/21 3:37 下午
 */
@Configuration
public class DelayedMessageRabbitConfig {
    @Bean
    public Queue delayQueue() {
        return new Queue("delayQueue", true, false, false);
    }

    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange("delayedExchange", "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding bindingDelay() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with("delayRouting").noargs();
    }
}