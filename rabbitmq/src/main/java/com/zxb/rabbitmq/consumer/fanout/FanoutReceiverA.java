/**
 * Copyright (C), 2015-2020
 */
package com.zxb.rabbitmq.consumer.fanout;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @author zhaoxb
 * @create 2020-04-24 14:05
 */
@Component
@RabbitListener(queues = "fanoutA")
@Slf4j
public class FanoutReceiverA {
    @RabbitHandler
    public void process(Map testMessage) {
        log.info("FanoutReceiverA消费者收到消息：{}", JSONUtil.toJsonPrettyStr(testMessage));
    }
}