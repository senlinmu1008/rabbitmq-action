package com.zxb.rabbitmq.web;

import cn.hutool.json.JSONUtil;
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
 * @date 2020/09/17 9:59 下午
 */
@RestController
public class MessageManualAckController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/manualAck")
    public String manualAck() {
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", String.valueOf(UUID.randomUUID()));
        map.put("messageData", "manualAck");
        map.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        rabbitTemplate.convertAndSend("directExchange", "manualAck", JSONUtil.toJsonStr(map));
        return "ok";
    }
}