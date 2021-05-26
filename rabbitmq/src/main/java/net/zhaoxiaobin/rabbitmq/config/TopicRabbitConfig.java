/**
 * Copyright (C), 2015-2020
 */
package net.zhaoxiaobin.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主题交换机，支持路由键通配的方式将消息发送给匹配的队列
 *
 * @author zhaoxb
 * @create 2020-04-24 10:45
 */
@Configuration
public class TopicRabbitConfig {

    @Bean
    public Queue firstQueue() {
        return new Queue("topicQueue1", true, false, false);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue("topicQueue2", true, false, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("topicExchange", true, false);
    }

    // 将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
    // 这样只要是消息携带的路由键是topic.man,才会分发到该队列
    @Bean
    public Binding bindingExchangeMessage() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with("topic.man");
    }

    // 将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
    @Bean
    public Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with("topic.#");
    }

}