package net.zhaoxiaobin.rabbitmq.web;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaoxb
 * @date 2020/09/21 8:32 下午
 */
@RestController
@Slf4j
public class DelayMessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/sendDelayedMessage")
    public String sendDelayedMessage() {
        log.info(DateUtil.now());
        rabbitTemplate.convertAndSend("delayedExchange", "delayRouting", "订单取消", message -> {
            message.getMessageProperties().setDelay(5000);
            return message;
        });
        return "ok";
    }
}