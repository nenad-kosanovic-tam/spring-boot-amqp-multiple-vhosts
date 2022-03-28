package com.example.rmqvhost.messaging.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@Service
public class DefaultVirtualHostConfiguration {

    public static final String V_HOST = "/";

    @Value("${rmq.host}")
    private String host;

    @Value("${rmq.port}")
    private int port;

    @Value("${rmq.user}")
    private String username;

    @Value("${rmq.pass}")
    private String password;

    @Bean
    public ConnectionFactory defaultConnectionFactory() {
        var connFactory = new CachingConnectionFactory(host, port);
        connFactory.setVirtualHost(V_HOST);
        connFactory.setUsername(username);
        connFactory.setPassword(password);
        return connFactory;
    }
}