package com.zxb.rabbitmq.native_dlx;

import cn.hutool.core.date.DateUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaoxb
 * @date 2020/08/04 8:24 下午
 */
@Slf4j
public class ProducerDLX {
    public static final String HOST = "148.70.153.63";
    public static final String USER_NAME = "libai";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);
        connectionFactory.setPort(AMQP.PROTOCOL.PORT);
        connectionFactory.setUsername(USER_NAME);
        connectionFactory.setPassword(System.getProperty("password"));
        connectionFactory.setVirtualHost(ConnectionFactory.DEFAULT_VHOST);

        // 创建连接和通道
        @Cleanup Connection connection = connectionFactory.newConnection();
        @Cleanup Channel channel = connection.createChannel();

        // 创建死信队列DLX
        String dlxExchangeName = "DLXExchange", dlxQueueName = "DLXQueue", dlxRoutingKey = "DLX";
        channel.exchangeDeclare(dlxExchangeName, BuiltinExchangeType.DIRECT, true);
        channel.queueDeclare(dlxQueueName, true, false, false, null);
        channel.queueBind(dlxQueueName, dlxExchangeName, dlxRoutingKey);

        // 创建消息会自动过期的队列，并和指定的死信交换机绑定
        String exchangeName = "amq.direct", queueName = "TestDLXQueue", routingKey = "DLX";
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("x-message-ttl", 30 * 1000); // 设置队列里消息的ttl的时间30s
        argMap.put("x-dead-letter-exchange", dlxExchangeName); // 给队列设置死信交换机
        channel.queueDeclare(queueName, true, false, false, argMap);
        channel.queueBind(queueName, exchangeName, routingKey);

        // 发送消息
        String msg = "测试死信队列";
        // 把消息发送到指定的交换机，交换机根据路由键推送到绑定的队列中；交换机名称、路由键、属性、消息字节
        log.info("now:[{}],发送消息:[{}]", DateUtil.now(), msg);
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
    }
}