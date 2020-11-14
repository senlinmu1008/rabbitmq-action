package com.zxb.rabbitmq.springboot_dlx;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaoxb
 * @date 2020/09/27 11:01 下午
 */
@RestController
public class DLXController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/testDLX")
    public String testDLX() {
        rabbitTemplate.convertAndSend("TEST_DLX_EXCHANGE", "DLX", "测试死信队列");
        return "ok";
    }
}