package com.halcyon.customer;

import com.halcyon.amqp.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class MessageListener {
    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consumer(String message) {
        log.info("Consumed {} from queue", message);
        System.out.println(message);
    }
}