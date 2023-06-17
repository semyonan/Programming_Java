package com.lab.messagebroker.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.dto.CarMarkDto;
import com.lab.dto.CarModelDto;
import com.lab.services.AccessService;
import com.lab.services.CarModelService;
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

    private final CarModelService carModelService;
    private final AccessService accessService;


    @RabbitListener(queues = "#{deleteQueue.name}")
    public void carModelDeleteListener(Long userId) {
        log.info("[MESSAGE BROKER] Received campaignsDelete message for id: " + userId);
        carModelService.delete(userId);
    }

    @RabbitListener(queues = "#{saveQueue.name}")
    public void carModelSaveListener(String messageObject) throws JsonProcessingException {
        CarModelDto carModelDto =  new ObjectMapper().readValue(messageObject, CarModelDto.class);
        carModelService.save(carModelDto);
    }

    @RabbitListener(queues = "#{updateAccessQueue.name}")
    public void accessChangeListener(Long userId) {
        accessService.setUserId(userId);
    }

    @RabbitListener(queues = "#{getQueue.name}")
    public String getCarModelListener(Long id){
        CarModelDto carModelDto = carModelService.getById(id);
        String messageString = null;
        try {
            messageString = new ObjectMapper().writeValueAsString(carModelDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return messageString;
    }

    @RabbitListener(queues = "#{getAllQueue.name}")
    public String getAllCarModelListener(String message) {
        List<CarModelDto> carModelDtoList = carModelService.getAll();
        String messageString = null;
        try {
            messageString = new ObjectMapper().writeValueAsString(carModelDtoList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return messageString;
    }
}
