package com.mailer;

import com.mailer.listener.RabbitMQMessageListener;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConnectionManager {
    private final static String QUEUE_NAME = "MailQueue";

    public void connect(RabbitMQMessageListener listener) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                listener.onMessageReciever(new String(body));
            }
        };

        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
