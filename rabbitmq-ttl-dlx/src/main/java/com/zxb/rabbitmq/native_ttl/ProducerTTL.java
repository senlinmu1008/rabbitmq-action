package com.zxb.rabbitmq.native_ttl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Cleanup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaoxb
 * @date 2020/08/04 8:24 下午
 */
public class ProducerTTL {
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

        String exchangeName = "amq.direct", queueName = "TTLQueue", routingKey = "TTL";
        // 声明交换机（如果不存在才创建），交换机名称、类型、交换机是否持久化
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("x-message-ttl", 30 * 1000); // 设置队列里消息的ttl的时间30s
        // 声明队列（如果不存在才创建），队列名称、队列是否持久化、是否排他（连接可见性）、是否自动删除（所有消费者断开连接后删除队列）、参数
        channel.queueDeclare(queueName, true, false, false, argMap);
        // 将队列和交换机绑定并指定路由键
        channel.queueBind(queueName, exchangeName, routingKey);

        // 发送消息
        String msg = "测试消息自动过期";
//        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
//                .deliveryMode(1) // 1-表示消息不做持久化，2-表示消息会持久化到磁盘（对性能会有些影响）
//                .expiration("30000") // 设置消息过期时间，单位：毫秒
//                .build();
        // 把消息发送到指定的交换机，交换机根据路由键推送到绑定的队列中；交换机名称、路由键、属性、消息字节
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
    }
}