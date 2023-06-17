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
        return new Queue("deleteEngine", false);
    }

    @Bean
    public FanoutExchange engineDeleteExchange() {
        return new FanoutExchange("engine.delete");
    }

    @Bean
    public Binding engineDeleteBinding() {
        return BindingBuilder.bind(deleteQueue()).to(engineDeleteExchange());
    }

    @Bean
    public Queue saveQueue() {
        return new Queue("saveEngine", false);
    }

    @Bean
    public FanoutExchange saveExchange() {
        return new FanoutExchange("engine");
    }

    @Bean
    public Binding engineSaveBinding() {
        return BindingBuilder.bind(saveQueue()).to(saveExchange());
    }

    @Bean
    public Queue updateAccessQueue() {
        return new Queue("accessEngine", false);
    }

    @Bean
    public FanoutExchange updateAccessExchange() {
        return new FanoutExchange("accessEngine.update");
    }

    @Bean
    public Binding updateAccessBinding() {
        return BindingBuilder.bind(updateAccessQueue()).to(updateAccessExchange());
    }
    @Bean
    public Queue getQueue() {
        return new Queue("getEngine", false);
    }

    @Bean
    public FanoutExchange getExchange() {
        return new FanoutExchange("engine.get");
    }

    @Bean
    public Binding getBinding() {
        return BindingBuilder.bind(getQueue()).to(getExchange());
    }
    @Bean
    public Queue getAllQueue() {
        return new Queue("getAllEngine", false);
    }

    @Bean
    public FanoutExchange getAllExchange() {
        return new FanoutExchange("allEngine.get");
    }

    @Bean
    public Binding getAllBinding() {
        return BindingBuilder.bind(getAllQueue()).to(getAllExchange());
    }
}