package com.zxb.rabbitmq.springboot_dlx;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaoxb
 * @date 2020/09/28 8:06 下午
 */
@Configuration
public class DLXQueueRabbitConfig {
    /**
     * 设置死信队列
     *
     * @return
     */
    @Bean
    public Queue DLXQueue() {
        return new Queue("DLX_QUEUE", true, false, false);
    }

    @Bean
    public DirectExchange DLXExchange() {
        return new DirectExchange("DLX_EXCHANGE", true, false);
    }

    @Bean
    public Binding bindingDLX() {
        return BindingBuilder.bind(DLXQueue()).to(DLXExchange()).with("DLX");
    }

    /**
     * 创建消息会自动过期的队列，并和指定的死信交换机绑定
     *
     * @return
     */
    @Bean
    public Queue testDLXQueue() {
        Map<String, Object> map = new HashMap<>();
        map.put("x-message-ttl", 30000); // 队列中的消息未被消费则30秒后过期
        map.put("x-dead-letter-exchange", "DLX_EXCHANGE"); // 给队列设置死信交换机
        return new Queue("TEST_DLX_QUEUE", true, false, false, map);
    }

    @Bean
    public DirectExchange testDLXExchange() {
        return new DirectExchange("TEST_DLX_EXCHANGE", true, false);
    }

    @Bean
    public Binding bindingTestDLX() {
        return BindingBuilder.bind(testDLXQueue()).to(testDLXExchange()).with("DLX");
    }
}