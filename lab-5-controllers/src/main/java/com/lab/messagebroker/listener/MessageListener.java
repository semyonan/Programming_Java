package com.lab.messagebroker.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.dto.RegisterRequest;
import com.lab.security.Registrator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {
    private final Registrator registrator;

    @RabbitListener(queues = "#{saveQueue.name}")
    public void registerChangeListener(String messageObject) throws JsonProcessingException {
        RegisterRequest registerRequest =  new ObjectMapper().readValue(messageObject, RegisterRequest.class);
        registrator.register(registerRequest);
    }
}
