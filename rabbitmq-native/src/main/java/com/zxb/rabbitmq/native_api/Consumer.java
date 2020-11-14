package com.zxb.rabbitmq.native_api;

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
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Producer producer = new Producer();
        // 获取connectionFactory
        ConnectionFactory connectionFactory = producer.getConnectionFactory();

        // 创建连接和通道
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        // 消费消息-客户端主动拉取模式、自动ACK确认
        // 性能较差，每次都要创建、关闭连接和通道。
//        GetResponse response = channel.basicGet("apiQueue1", true);
//        String msg = Optional.ofNullable(response).map(GetResponse::getBody).map(String::new).orElse(null);
//        log.info("消费消息:[{}]", msg);
//        channel.close();
//        connection.close();

        // 消费消息-server推送模式，创建连接和通道后，等待队列推送消息然后进行消费
        // 不能关闭连接和通道
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                long now = System.currentTimeMillis();
                if (now % 3 == 0) {
                    log.info("手动确认消费消息:[{}]", new String(body));
                    // 消息唯一标记、是否确认多条（true则批量确认小于当前标记号的所有消息）
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } else if (now % 3 == 1) {
                    log.info("basicNack:[手动拒绝消息，重回队列]");
                    // 消息唯一标记、是否拒绝多条（true则批量拒绝小于当前标记号的所有消息）、是否重回队列
                    // 重回队列后仍旧会被当前消费者再次消费
                    channel.basicNack(envelope.getDeliveryTag(), false, true);
                } else if (now % 3 == 2) {
                    log.info("basicReject:[手动拒绝消息，重回队列]");
                    // 只能拒绝单条消息，消息唯一标记、是否重回队列
                    // 重回队列后仍旧会被当前消费者再次消费
                    channel.basicReject(envelope.getDeliveryTag(), true);
                }
            }
        };
        // 进入消费状态、手动ACK
        channel.basicConsume("apiQueue1", false, consumer);
    }
}