package com.lab.messagebroker.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.dto.CarMarkDto;
import com.lab.dto.RegisterRequest;
import com.lab.services.CarMarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendRegisterMessage(RegisterRequest register) {
        String exchangeName = "user";
        String messageString = null;
        try {
            messageString = new ObjectMapper().writeValueAsString(register);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("[MESSAGE BROKER] Sending company info to exchange " + exchangeName + ": " + messageString);
        rabbitTemplate.convertAndSend(exchangeName, "", messageString);
    }
}
