package com.h2lib.activity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class RabbitMQConfig {

    private final RabbitMQProperties rabbitMQProperties;

    @Bean
    public Queue queueActivity() {
        return QueueBuilder.durable(rabbitMQProperties.getQueueName())
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", "dead-letter-queue")
                .build();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(rabbitMQProperties.getExchange());
    }

    @Bean
    public Binding binding(Queue queueActivity, TopicExchange exchange) {
        return BindingBuilder.bind(queueActivity)
                .to(exchange)
                .with(rabbitMQProperties.getRoutingKey());
    }

    @Bean
    public MessageConverter messageConverter(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue("dead-letter-queue");
    }
}