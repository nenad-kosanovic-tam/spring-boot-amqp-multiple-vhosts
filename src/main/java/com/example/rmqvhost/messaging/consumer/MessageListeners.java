/*
 * Copyright (C) Tamedia AG 2020
 */

package com.example.rmqvhost.messaging.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Consumes messages from two different virtual hosts.
 *
 * @author Nenad Kosanovic
 */
@Component
@Slf4j
public class MessageListeners {

    public static final String EXCHANGE_NAME = "exchange-name";
    private static final String QUEUE_NAME = "queue-name";


    /**
     * Listener for received messages on default vHost.
     *
     * @param msg message
     */
    @RabbitListener(
            id = "default-vhost-queue",
            bindings = @QueueBinding(
                    value = @Queue(value = QUEUE_NAME, durable = "true"),
                    exchange = @Exchange(value = EXCHANGE_NAME, type = "topic", ignoreDeclarationExceptions = "true"),
                    key = "fromController")
    )
    private void receiveMessageDefaultHost(@Payload final String msg) {
        log.info("Message received on default vhost: {}", msg);
    }

    /**
     * Listener for received collection messages.
     *
     * @param msg message
     */
    @RabbitListener(
            containerFactory = "customVirtualHostRabbitListenerContainerFactory",
            id = "custom-vhost-queue",
            bindings = @QueueBinding(
                    value = @Queue(value = QUEUE_NAME, durable = "true"),
                    exchange = @Exchange(value = EXCHANGE_NAME, type = "topic", ignoreDeclarationExceptions = "true"),
                    key = "fromController")
    )
    private void receiveMessageCustomHost(@Payload final String msg) {
        log.info("Message received on custom vhost: {}", msg);
    }

}

