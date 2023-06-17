package com.lab.messagebroker.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.dto.CarModelDto;
import com.lab.dto.EngineDto;
import com.lab.services.AccessService;
import com.lab.services.EngineService;
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

    private final EngineService engineService;
    private final AccessService accessService;
    @RabbitListener(queues = "#{deleteQueue.name}")
    public void engineDeleteListener(Long userId) {
        log.info("[MESSAGE BROKER] Received campaignsDelete message for id: " + userId);
        engineService.delete(userId);
    }

    @RabbitListener(queues = "#{saveQueue.name}")
    public void engineSaveListener(String messageObject) throws JsonProcessingException {
        EngineDto engineDto =  new ObjectMapper().readValue(messageObject, EngineDto.class);
        engineService.save(engineDto);
    }

    @RabbitListener(queues = "#{updateAccessQueue.name}")
    public void accessChangeListener(Long userId) {
        accessService.setUserId(userId);
    }

    @RabbitListener(queues = "#{getQueue.name}")
    public String getEngineListener(Long id){
        EngineDto engineDto = engineService.getById(id);
        String messageString = null;
        try {
            messageString = new ObjectMapper().writeValueAsString(engineDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return messageString;
    }

    @RabbitListener(queues = "#{getAllQueue.name}")
    public String getAllEngineListener(String message) {
        List<EngineDto> engineDtoList = engineService.getAll();
        String messageString = null;
        try {
            messageString = new ObjectMapper().writeValueAsString(engineDtoList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return messageString;
    }
}
