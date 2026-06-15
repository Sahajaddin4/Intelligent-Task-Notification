package org.taskmanagement.taskservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.taskmanagement.taskservice.config.RabbitMqConfig;
import org.taskmanagement.taskservice.dto.message.RemindUserForTaskDto;

@Service
@Slf4j
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;
    public MessageProducer(@Autowired RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async("rabbitWorkerAsync")
    public void remindUserToComplete(RemindUserForTaskDto remindUserForTaskDto) {
        log.info("Reminding user to complete for {}", remindUserForTaskDto);
        rabbitTemplate.convertAndSend(RabbitMqConfig.REMIND_TASK_EXCHANGE_NAME,RabbitMqConfig.REMIND_TASK_ROUTING_KEY,remindUserForTaskDto);
    }
}
