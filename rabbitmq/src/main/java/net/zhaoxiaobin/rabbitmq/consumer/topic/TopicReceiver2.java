/**
 * Copyright (C), 2015-2020
 */
package net.zhaoxiaobin.rabbitmq.consumer.topic;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @author zhaoxb
 * @create 2020-04-24 13:28
 */
@Component
@RabbitListener(queues = "topicQueue2")
@Slf4j
public class TopicReceiver2 {
    @RabbitHandler
    public void process(Map testMessage) {
        log.info("TopicReceiver2消费者收到消息：{}", JSONUtil.toJsonPrettyStr(testMessage));
    }
}