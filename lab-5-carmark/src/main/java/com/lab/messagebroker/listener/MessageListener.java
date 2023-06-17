package com.lab.messagebroker.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.dto.CarMarkDto;
import com.lab.services.CarMarkService;
import entities.CarMark;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListener {

    private final CarMarkService carMarkService;

    @RabbitListener(queues = "#{deleteQueue.name}")
    public void carMarkDeleteListener(Long userId) {
        log.info("[MESSAGE BROKER] Received campaignsDelete message for id: " + userId);
        carMarkService.delete(userId);
    }

    @RabbitListener(queues = "#{saveQueue.name}")
    public void saveCarMarkListener(String messageObject) throws JsonProcessingException {
        CarMarkDto carMarkDto = new ObjectMapper().readValue(messageObject, CarMarkDto.class);
        carMarkService.save(carMarkDto);
    }

    @RabbitListener(queues = "#{getQueue.name}")
    public String getCarMarkListener(Long id){
        CarMarkDto carMarkDto = carMarkService.getById(id);
        String messageString = null;
        try {
            messageString = new ObjectMapper().writeValueAsString(carMarkDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return messageString;
    }

    @RabbitListener(queues = "#{getAllQueue.name}")
    public String getAllCarMarkListener(String message) {
        List<CarMarkDto> carMarkDtoList = carMarkService.getAll();
        String messageString = null;
        try {
            messageString = new ObjectMapper().writeValueAsString(carMarkDtoList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return messageString;
    }
}
