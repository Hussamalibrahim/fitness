package com.h2lib.activity.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Getter
public class RabbitMQProperties {

    @Value("${rabbit.mq.exchange.name}")
    private String exchange;

    @Value("${rabbit.mq.queue.message}")
    private String queueName;

    @Value("${rabbit.mq.routing.key.message}")
    private String routingKey;
}
