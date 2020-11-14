package com.zxb.rabbitmq.native_dlx;

import cn.hutool.core.date.DateUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaoxb
 * @date 2020/08/04 9:35 下午
 */
@Slf4j
public class ConsumerDLX {
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
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("now:[{}],消费消息:[{}]", DateUtil.now(), new String(body));
            }
        };
        channel.basicConsume("DLXQueue", true, consumer);
    }
}