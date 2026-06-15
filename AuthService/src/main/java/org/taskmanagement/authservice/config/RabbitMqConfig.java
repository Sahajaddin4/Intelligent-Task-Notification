package org.taskmanagement.authservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String SIGNUP_QUEUE = "signup_queue";
    public static final String SIGNUP_EXCHANGE = "signup_exchange";
    public static final String SIGNUP_ROUTING_KEY = "signup_routing_key";
    public static final String USER_CREATED_EXCHANGE_NAME = "user_created_exchange";
    public static final String USER_CREATED_QUEUE_NAME = "user_created_queue";
    public static final String USER_CREATED_ROUTING_KEY = "user_created_routing_key";
    
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
    @Bean
    public Queue signupQueue() {
        return new Queue(SIGNUP_QUEUE,true,false,false);
    }
    @Bean
    public Exchange signupExchange() {
        return new DirectExchange(SIGNUP_EXCHANGE);
    }

    @Bean
    public Binding signupBinding(Queue signupQueue, Exchange signupExchange) {
            return BindingBuilder.bind(signupQueue).to(signupExchange).with(SIGNUP_ROUTING_KEY).noargs();
    }
    
    @Bean
    public Queue userCreatedQueue() {
//        return new Queue(USER_CREATED_QUEUE_NAME,true,false,false);
        return QueueBuilder.durable(USER_CREATED_QUEUE_NAME).deadLetterExchange("_dead_letter_"+USER_CREATED_EXCHANGE_NAME)
                .deadLetterRoutingKey("_dead_letter_"+USER_CREATED_ROUTING_KEY).build();
    }

    @Bean
    public Exchange userCreatedExchange() {
        return new DirectExchange(USER_CREATED_EXCHANGE_NAME);
    }
    @Bean
    public Binding userCreatedBinding(Queue userCreatedQueue, Exchange userCreatedExchange) {
        return BindingBuilder.bind(userCreatedQueue).to(userCreatedExchange).with(USER_CREATED_ROUTING_KEY).noargs();
    }
}
