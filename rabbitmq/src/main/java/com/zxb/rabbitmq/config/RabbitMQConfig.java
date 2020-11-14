/**
 * Copyright (C), 2015-2020
 */
package com.zxb.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaoxb
 * @create 2020-04-24 14:31
 */
@Configuration
@Slf4j
public class RabbitMQConfig {
    /**
     * 1. 消息推送到server，但是在server里找不到交换机
     * 2. 消息推送到server，找到交换机了，但是没找到队列
     * 3. 消息推送成功
     */
    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        // 设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        // 确认消息已发送到交换机(Exchange)
        // 1、2、3都会触发此回调函数
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("ConfirmCallback:     " + "相关数据：" + correlationData);
            log.info("ConfirmCallback:     " + "确认情况：" + ack);
            log.info("ConfirmCallback:     " + "原因：" + cause);
        });

        // 确认消息已发送到队列(Queue)
        // 只有2才会触发此回调函数
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("ReturnCallback:     " + "消息：" + message);
            log.info("ReturnCallback:     " + "回应码：" + replyCode);
            log.info("ReturnCallback:     " + "回应信息：" + replyText);
            log.info("ReturnCallback:     " + "交换机：" + exchange);
            log.info("ReturnCallback:     " + "路由键：" + routingKey);
        });
        return rabbitTemplate;
    }
}