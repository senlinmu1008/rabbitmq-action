package com.zxb.rabbitmq.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author zhaoxb
 * @date 2020/09/22 12:42 下午
 */
@RestController
@Slf4j
public class RPCController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AsyncRabbitTemplate asyncRabbitTemplate;

    @PostConstruct
    public void init() {
        // 同步调用设置远程调用响应超时时间，单位：毫秒
        rabbitTemplate.setReplyTimeout(60000);
    }

    @PostMapping("/syncRPC")
    public String syncRPC() {
        Object response = rabbitTemplate.convertSendAndReceive("RPCExchange", "RPC", "RPC同步调用");
        String respMsg = response.toString();
        log.info("远程调用响应:[{}]", respMsg);
        return respMsg;
    }

    @PostMapping("/asyncRPC")
    public String asyncRPC() {
        AsyncRabbitTemplate.RabbitConverterFuture<Object> future = asyncRabbitTemplate.convertSendAndReceive("RPCExchange", "RPC", "RPC异步调用");
        future.addCallback(new ListenableFutureCallback<Object>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("异步调用失败", throwable);
            }

            @Override
            public void onSuccess(Object o) {
                log.info("异步调用响应:[{}}", o.toString());
            }
        });
        return "ok";
    }
}