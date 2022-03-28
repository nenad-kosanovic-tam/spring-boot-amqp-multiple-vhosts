package com.example.rmqvhost.messaging.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Map;

@Configuration
public class GlobalConfiguration {

    @Autowired
    private ConnectionFactory defaultConnectionFactory;

    @Autowired
    private ConnectionFactory customVirtualHostConnectionFactory;

    @Bean
    @Primary
    public SimpleRoutingConnectionFactory routingConnectionFactory() {
        final var rcf = new SimpleRoutingConnectionFactory();
        rcf.setTargetConnectionFactories(Map.of(
                DefaultVirtualHostConfiguration.V_HOST, defaultConnectionFactory,
                CustomVirtualHostConfiguration.V_HOST, customVirtualHostConnectionFactory
        ));
        rcf.setDefaultTargetConnectionFactory(defaultConnectionFactory);
        return rcf;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(routingConnectionFactory());
    }
}
