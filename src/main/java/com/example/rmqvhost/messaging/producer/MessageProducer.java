package com.example.rmqvhost.messaging.producer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.SimpleResourceHolder;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.example.rmqvhost.messaging.consumer.MessageListeners.EXCHANGE_NAME;

@Slf4j
@AllArgsConstructor
@Service
public class MessageProducer {

    private final RabbitMessagingTemplate rabbitMessagingTemplate;

    /**
     * Send message to RabbitMQ.
     *
     * @param vHost   virtual host
     * @param message message to be sent
     */
    public void send(final String vHost, final String message) {
        try {
            SimpleResourceHolder.bind(rabbitMessagingTemplate.getRabbitTemplate().getConnectionFactory(), vHost);
            rabbitMessagingTemplate.convertAndSend(EXCHANGE_NAME, "fromController", message);
        } finally {
            SimpleResourceHolder.unbind(rabbitMessagingTemplate.getRabbitTemplate().getConnectionFactory());
        }
    }
}