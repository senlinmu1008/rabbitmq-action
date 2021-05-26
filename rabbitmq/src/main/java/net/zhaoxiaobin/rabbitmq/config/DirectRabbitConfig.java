/**
 * Copyright (C), 2015-2020
 */
package net.zhaoxiaobin.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 直连交换机，交换机和队列绑定的路由键需要和消息携带的路由键完全一致才会发送消息到队列上
 *
 * @author zhaoxb
 * @create 2020-03-24 14:02
 */
@Configuration
public class DirectRabbitConfig {
    //队列 起名：directQueue
    @Bean
    public Queue directQueue() {
        return new Queue("directQueue", true, false, false);
    }

    //Direct交换机 起名：directExchange
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange", true, false);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：directRouting
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("directRouting");
    }

    //需要手动确认消息的队列
    @Bean
    public Queue manualAckQueue() {
        return new Queue("manualAckQueue", true, false, false);
    }

    //手动确认消息的队列和直连交换机绑定
    @Bean
    public Binding bindingDirectForManualAck() {
        return BindingBuilder.bind(manualAckQueue()).to(directExchange()).with("manualAck");
    }
}