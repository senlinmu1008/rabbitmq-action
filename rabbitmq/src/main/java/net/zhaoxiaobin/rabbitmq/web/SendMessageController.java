/**
 * Copyright (C), 2015-2020
 */
package net.zhaoxiaobin.rabbitmq.web;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhaoxb
 * @create 2020-03-26 14:12
 */
@RestController
public class SendMessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;  // 使用RabbitTemplate,这提供了接收/发送等等方法

    @PostMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", String.valueOf(UUID.randomUUID()));
        map.put("messageData", "RabbitMQ");
        map.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // 将消息携带路由键值：directRouting 发送到交换机directExchange
        rabbitTemplate.convertAndSend("directExchange", "directRouting", map);
        return "ok";
    }

    @PostMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", String.valueOf(UUID.randomUUID()));
        map.put("messageData", "testFanoutMessage");
        map.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        rabbitTemplate.convertAndSend("fanoutExchange", null, map);
        return "ok";
    }

    @PostMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", String.valueOf(UUID.randomUUID()));
        manMap.put("messageData", "man");
        manMap.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // 将消息携带路由键值：topic.man 发送到交换机topicExchange
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", manMap);
        return "ok";
    }

    @PostMapping("/sendTopicMessage2")
    public String sendTopicMessage2() {
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", String.valueOf(UUID.randomUUID()));
        womanMap.put("messageData", "woman");
        womanMap.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // 将消息携带路由键值：topic.woman 发送到交换机topicExchange
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap);
        return "ok";
    }
}