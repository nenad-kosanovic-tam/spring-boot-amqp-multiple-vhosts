package com.example.rmqvhost.messaging.controller;

import com.example.rmqvhost.messaging.config.DefaultVirtualHostConfiguration;
import com.example.rmqvhost.messaging.service.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.rmqvhost.messaging.consumer.MessageListeners.EXCHANGE_NAME;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/messages")
public class MessageProducerController {

    @Autowired
    MessageProducer messageProducer;

    @PostMapping
    @ResponseStatus(CREATED)
    public void sendMessage(@RequestBody String message) {
        messageProducer.send(EXCHANGE_NAME, "fromController", DefaultVirtualHostConfiguration.V_HOST, message);
    }

    @PostMapping("/{vHost}")
    @ResponseStatus(CREATED)
    public void sendMessage(@PathVariable String vHost, @RequestBody String message) {
        messageProducer.send(EXCHANGE_NAME, "fromController", vHost, message);

    }
}
