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
    public Queue deleteQueue() {
        return new Queue("deleteCarMark", false);
    }

    @Bean
    public FanoutExchange userDeleteExchange() {
        return new FanoutExchange("carMark.delete");
    }

    @Bean
    public Binding userDeleteBinding() {
        return BindingBuilder.bind(deleteQueue()).to(userDeleteExchange());
    }

    @Bean
    public Queue saveQueue() {
        return new Queue("saveCarMark", false);
    }

    @Bean
    public FanoutExchange saveExchange() {
        return new FanoutExchange("carMark");
    }

    @Bean
    public Binding userAddBinding() {
        return BindingBuilder.bind(saveQueue()).to(saveExchange());
    }

    @Bean
    public Queue getQueue() {
        return new Queue("getCarMark", false);
    }

    @Bean
    public FanoutExchange getExchange() {
        return new FanoutExchange("carMark.get");
    }

    @Bean
    public Binding getBinding() {
        return BindingBuilder.bind(getQueue()).to(getExchange());
    }
    @Bean
    public Queue getAllQueue() {
        return new Queue("getAllCarMark", false);
    }

    @Bean
    public FanoutExchange getAllExchange() {
        return new FanoutExchange("allCarMark.get");
    }

    @Bean
    public Binding getAllBinding() {
        return BindingBuilder.bind(getAllQueue()).to(getAllExchange());
    }
}