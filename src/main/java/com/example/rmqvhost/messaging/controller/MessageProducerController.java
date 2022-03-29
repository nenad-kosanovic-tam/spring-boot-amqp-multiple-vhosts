package com.example.rmqvhost.messaging.controller;

import com.example.rmqvhost.messaging.config.DefaultVirtualHostConfiguration;
import com.example.rmqvhost.messaging.model.Message;
import com.example.rmqvhost.messaging.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.example.rmqvhost.messaging.consumer.MessageListeners.EXCHANGE_NAME;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/messages")
public class MessageProducerController {

    @Autowired
    MessageProducer messageProducer;

    @PostMapping
    @ResponseStatus(CREATED)
    public void sendMessage(@RequestBody Message message) {
        messageProducer.send(DefaultVirtualHostConfiguration.V_HOST, getMessage(message));
    }

    @PostMapping("/{vHost}")
    @ResponseStatus(CREATED)
    public void sendMessage(@PathVariable String vHost, @RequestBody Message message) {
        messageProducer.send(vHost, getMessage(message));

    }

    private String getMessage(Message message) {
        return Optional.ofNullable(message).map(Message::getText).orElse("Empty message!");
    }
}
