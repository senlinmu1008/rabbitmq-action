package com.zxb.rabbitmq.springboot_ttl;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaoxb
 * @date 2020/09/27 11:01 下午
 */
@RestController
public class TTLController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/testTTL")
    public String testTTL() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("20000"); // 设置过期时间，单位：毫秒
        byte[] msgBytes = "测试消息自动过期".getBytes();
        Message message = new Message(msgBytes, messageProperties);
        rabbitTemplate.convertAndSend("TTL_EXCHANGE", "TTL", message);
        return "ok";
    }
}