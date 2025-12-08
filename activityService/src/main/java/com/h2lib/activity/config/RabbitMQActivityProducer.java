package com.h2lib.activity.config;


import com.h2lib.activity.model.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQActivityProducer {

    @Autowired
    private RabbitMQProperties rabbitMQProperties;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(Activity message) {
        try {
            // Validate message before sending
            if (message == null || message.getUserId() == null) {
                log.error("Cannot send null or invalid activity message");
                return;
            }

            log.info("Sending activity to RabbitMQ - User: {}, Activity: {}",
                    message.getUserId(), message.getTypeActivity());

            rabbitTemplate.convertAndSend(
                    rabbitMQProperties.getExchange(),
                    rabbitMQProperties.getRoutingKey(),
                    message,
                    m -> {
                        m.getMessageProperties().setContentType("application/json");
                        return m;
                    }
            );

            log.info("Successfully sent activity message to RabbitMQ");

        } catch (Exception e) {
            log.error("Failed to send activity message to RabbitMQ: {}", message, e);
            throw new RuntimeException("Failed to send message to RabbitMQ", e);
        }
    }
}
