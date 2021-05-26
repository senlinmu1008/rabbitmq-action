package net.zhaoxiaobin.rabbitmq.consumer.rpc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhaoxb
 * @date 2020/09/22 12:39 下午
 */
@Component
@RabbitListener(queues = "RPCQueue")
@Slf4j
public class RPCReceiver {
    @RabbitHandler
    public String process(String message) {
        log.info("接收远程调用请求消息:[{}]", message);
        return "remote procedure call success!";
    }
}