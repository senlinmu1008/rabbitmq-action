package net.zhaoxiaobin.rabbitmq.native_api;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaoxb
 * @date 2020/08/04 8:24 下午
 */
public class Producer {
    public static final String HOST = "148.70.153.63";
    public static final String USER_NAME = "libai";

    public static void main(String[] args) throws IOException, TimeoutException {
        Producer producer = new Producer();
        // 获取connectionFactory
        ConnectionFactory connectionFactory = producer.getConnectionFactory();

        // 创建连接和通道
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "amq.direct", queueName = "apiQueue1", routingKey = "RabbitMQ";
        // 声明交换机（如果不存在才创建），交换机名称、类型、交换机是否持久化
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);
        // 声明队列（如果不存在才创建），队列名称、队列是否持久化、是否排他（连接可见性）、是否自动删除（所有消费者断开连接后删除队列）、参数
        channel.queueDeclare(queueName, true, false, false, null);
        // 将队列和交换机绑定并指定路由键
        channel.queueBind(queueName, exchangeName, routingKey);

        // 发送消息
        String msg = "Hello RabbitMQ!";
        // 把消息发送到指定的交换机，交换机根据路由键推送到绑定的队列中；交换机名称、路由键、属性、消息字节
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        // 释放连接
        channel.close();
        connection.close();
    }

    /**
     * 获取连接工厂
     *
     * @return
     */
    public ConnectionFactory getConnectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);
        connectionFactory.setPort(AMQP.PROTOCOL.PORT);
        connectionFactory.setUsername(USER_NAME);
        connectionFactory.setPassword(System.getProperty("password"));
        connectionFactory.setVirtualHost(ConnectionFactory.DEFAULT_VHOST);
        return connectionFactory;
    }
}