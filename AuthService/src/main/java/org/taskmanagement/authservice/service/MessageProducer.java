package org.taskmanagement.authservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.taskmanagement.authservice.config.RabbitMqConfig;
import org.taskmanagement.authservice.dto.message.SignupExchangeData;
import org.taskmanagement.authservice.dto.servicedto.request.CreateUserAccount;

@Slf4j
@Service
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;
    public MessageProducer(@Autowired RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async("rabbitMessageBroker")
    public void sendSuccessSignUpMessage(SignupExchangeData signupExchangeData) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.SIGNUP_EXCHANGE, RabbitMqConfig.SIGNUP_ROUTING_KEY, signupExchangeData);
        log.info("Message sent to send mail ");
    }

    @Async("rabbitMessageBroker")
    public  void sendDetailsToUserService(CreateUserAccount signupRequestDto) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.USER_CREATED_EXCHANGE_NAME,RabbitMqConfig.USER_CREATED_ROUTING_KEY,signupRequestDto);
        log.info("User details has been sent to User Service successfully");
    }
}
