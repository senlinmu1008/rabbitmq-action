/**
 * Copyright (C), 2015-2020
 */
package net.zhaoxiaobin.rabbitmq.manual;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaoxb
 * @create 2020-04-24 15:20
 */
@Configuration
@Slf4j
public class MessageManualAckListenerConfig {
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // RabbitMQ默认是自动确认，这里改为手动确认消息
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // 设置需要手动确认消息的队列，可以同时设置多个，前提是队列需要提前创建好
        container.setQueueNames("manualAckQueue");
        // 设置监听消息的方法，匿名内部类方式
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            // 开始消费消息
            log.info("body:\n{}", JSONUtil.toJsonPrettyStr(new String(message.getBody())));
            log.info("prop:\n{}", JSONUtil.toJsonPrettyStr(message.getMessageProperties()));

            // 手动确认
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            channel.basicAck(deliveryTag, false); // 肯定确认
//            channel.basicNack(deliveryTag, false, true); // 用于否定确认
//            channel.basicReject(deliveryTag, true); // 用于否定确认，为true会重新放回队列
        });
        return container;
    }
}