package com.h2lib.activity.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQHealthCheck {

    @Autowired
    private ConnectionFactory connectionFactory;

    @EventListener(ApplicationReadyEvent.class)
    public void checkRabbitMQConnection() {
        try {
            Connection connection = connectionFactory.createConnection();
            Channel channel = connection.createChannel(false);

            channel.queueDeclarePassive("activity_to_ai_queue");

            log.info("RabbitMQ connection established successfully");
            channel.close();
            connection.close();

        } catch (Exception e) {
            log.error("Failed to connect to RabbitMQ: {}", e.getMessage());
        }
    }
}