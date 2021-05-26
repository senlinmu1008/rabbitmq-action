/**
 * Copyright (C), 2015-2020
 */
package net.zhaoxiaobin.rabbitmq.springboot_ttl;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置消息自动过期
 *
 * @author zhaoxb
 * @create 2020-03-24 14:02
 */
@Configuration
public class TTLQueueRabbitConfig {
    @Bean
    public Queue TTLQueue() {
        Map<String, Object> map = new HashMap<>();
        map.put("x-message-ttl", 30000); // 队列中的消息未被消费则30秒后过期
        return new Queue("TTL_QUEUE", true, false, false, map);
    }

    @Bean
    public DirectExchange TTLExchange() {
        return new DirectExchange("TTL_EXCHANGE", true, false);
    }

    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(TTLQueue()).to(TTLExchange()).with("TTL");
    }
}