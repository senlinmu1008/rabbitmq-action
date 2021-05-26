/**
 * Copyright (C), 2015-2020
 */
package net.zhaoxiaobin.rabbitmq.consumer.direct;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @author zhaoxb
 * @create 2020-03-26 14:08
 */
@Component
@RabbitListener(queues = "directQueue")
@Slf4j
public class DirectReceiver {
    @RabbitHandler
    public void process(Map testMessage) {
        log.info("DirectReceiver消费者收到消息：{}", JSONUtil.toJsonPrettyStr(testMessage));
    }
}