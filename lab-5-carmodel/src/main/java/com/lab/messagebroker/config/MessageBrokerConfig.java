package com.lab.messagebroker.config;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.policy.SimpleRetryPolicy;

import java.util.Map;


@Configuration
public class MessageBrokerConfig {
    @Bean
    public RabbitRetryTemplateCustomizer customizeRetryPolicy(@Value("${spring.rabbitmq.listener.simple.retry.max-attempts}") int maxAttempts) {
        SimpleRetryPolicy policy = new SimpleRetryPolicy(maxAttempts, Map.of(AmqpRejectAndDontRequeueException.class, false), true, true);
        return (target, retryTemplate) -> retryTemplate.setRetryPolicy(policy);
    }
    @Bean
    public Queue deleteQueue() {
        return new Queue("deleteCarModel", false);
    }

    @Bean
    public FanoutExchange carModelDeleteExchange() {
        return new FanoutExchange("carModel.delete");
    }

    @Bean
    public Binding carModelDeleteBinding() {
        return BindingBuilder.bind(deleteQueue()).to(carModelDeleteExchange());
    }

    @Bean
    public Queue saveQueue() {
        return new Queue("saveCarModel", false);
    }

    @Bean
    public FanoutExchange saveExchange() {
        return new FanoutExchange("carModel");
    }

    @Bean
    public Binding carModelBinding() {
        return BindingBuilder.bind(saveQueue()).to(saveExchange());
    }

    @Bean
    public Queue updateAccessQueue() {
        return new Queue("accessCarModel", false);
    }

    @Bean
    public FanoutExchange updateAccessExchange() {
        return new FanoutExchange("accessCarModel.update");
    }

    @Bean
    public Binding updateAccessBinding() {
        return BindingBuilder.bind(updateAccessQueue()).to(updateAccessExchange());
    }
    @Bean
    public Queue getQueue() {
        return new Queue("getCarModel", false);
    }

    @Bean
    public FanoutExchange getExchange() {
        return new FanoutExchange("carModel.get");
    }

    @Bean
    public Binding getBinding() {
        return BindingBuilder.bind(getQueue()).to(getExchange());
    }
    @Bean
    public Queue getAllQueue() {
        return new Queue("getAllCarModel", false);
    }

    @Bean
    public FanoutExchange getAllExchange() {
        return new FanoutExchange("allCarModel.get");
    }

    @Bean
    public Binding getAllBinding() {
        return BindingBuilder.bind(getAllQueue()).to(getAllExchange());
    }
}