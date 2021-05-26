package net.zhaoxiaobin.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaoxb
 * @date 2020/09/22 12:35 下午
 */
@Configuration
public class RPCRabbitConfig {
    @Bean
    public Queue RPCQueue() {
        return new Queue("RPCQueue", true, false, false);
    }

    @Bean
    public DirectExchange RPCExchange() {
        return new DirectExchange("RPCExchange", true, false);
    }

    @Bean
    public Binding bindingRPC() {
        return BindingBuilder.bind(RPCQueue()).to(RPCExchange()).with("RPC");
    }

    /**
     * 配置AsyncRabbitTemplate SpringBoot 没有默认的AsyncRabbitTemplate注入，所以这里需要自己配置
     *
     * @param rabbitTemplate
     * @return
     */
    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate) {
        return new AsyncRabbitTemplate(rabbitTemplate);
    }
}