package org.taskmanagement.taskservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String REMIND_TASK_EXCHANGE_NAME = "remind-task-exchange";
    public static final String REMIND_TASK_QUEUE_NAME = "remind-task-queue";
    public static final String REMIND_TASK_ROUTING_KEY = "remind-task-routing-key";

    @Bean
    public MessageConverter messageConverter(@Autowired ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(@Autowired ConnectionFactory connectionFactory,  MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Exchange remindTaskExchange() {
        return ExchangeBuilder.directExchange(REMIND_TASK_EXCHANGE_NAME).build();
    }
    @Bean
    public Queue remindTaskQueue() {
        return QueueBuilder.durable(REMIND_TASK_QUEUE_NAME).
                deadLetterExchange("_dead_letter_"+REMIND_TASK_EXCHANGE_NAME).
                deadLetterRoutingKey("_dead_letter_"+REMIND_TASK_EXCHANGE_NAME).
                build();
    }
    @Bean
    public Binding  remindTaskBinding(Exchange remindTaskExchange, Queue remindTaskQueue) {
        return BindingBuilder.bind(remindTaskQueue).to(remindTaskExchange).with(REMIND_TASK_ROUTING_KEY).noargs();
    }
}
