package com.zxb.rabbitmq.consumer.direct;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhaoxb
 * @date 2020/09/21 8:43 下午
 */
@Component
@RabbitListener(queues = "delayQueue")
@Slf4j
public class DelayReceiver {
    @RabbitHandler
    public void process(String delayMessage) {
        log.info(DateUtil.now());
        log.info("延迟收到消息:{}", delayMessage);
    }
}