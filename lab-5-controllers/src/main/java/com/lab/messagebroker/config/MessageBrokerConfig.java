package com.lab.messagebroker.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MessageBrokerConfig {
    @Bean
    public Queue saveQueue() {
        return new Queue("saveUser", false);
    }

    /**
     * Fanout exchange configuration bean
     *
     * @return fanout exchange for messages including a company object, which can be received by multiple applications
     * that connect to the exchange with a queue.
     */
    @Bean
    public FanoutExchange saveExchange() {
        return new FanoutExchange("user");
    }

    /**
     * Queue to fanout exchange binding configuration bean
     *
     * @return binding between companyQueue and companyExchange, which means that when a message is sent to "company",
     * a copy of that message will be sent to "campaignsCompany", which the application can listen for
     */
    @Bean
    public Binding userAddBinding() {
        return BindingBuilder.bind(saveQueue()).to(saveExchange());
    }
}