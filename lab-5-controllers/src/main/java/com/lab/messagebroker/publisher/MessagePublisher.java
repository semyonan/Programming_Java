package com.lab.messagebroker.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.dto.CarMarkDto;
import com.lab.dto.CarModelDto;
import com.lab.dto.EngineDto;
import com.lab.dto.RegisterRequest;
import entities.Engine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.asm.TypeReference;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendCarModelAccessMessage(Long userId) {
        rabbitTemplate.convertAndSend("accessCarModel.update", "", userId);
    }

    public void sendEngineAccessMessage(Long userId) {
        rabbitTemplate.convertAndSend("accessEngine.update", "", userId);
    }

    public CarMarkDto getCarMarkMessage(Long userId) {
        CarMarkDto carMarkDto = null;
        String messageObject = (String) rabbitTemplate.convertSendAndReceive("carMark.get", "", userId);
        try {
            carMarkDto = new ObjectMapper().readValue(messageObject, CarMarkDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return carMarkDto;
    }

    public List<CarMarkDto> getAllCarMarkMessage() {
        List<CarMarkDto> carMarkDtoList = null;
        String messageObject = (String) rabbitTemplate.convertSendAndReceive("allCarMark.get", "", "");
        try {
            ObjectMapper mapper = new ObjectMapper();
            carMarkDtoList = mapper.readValue(messageObject, mapper.getTypeFactory().constructCollectionType(List.class, CarMarkDto.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return carMarkDtoList;
    }

    public CarModelDto getCarModelMessage(Long userId) {
        CarModelDto carModelDto = null;
        String messageObject = (String) rabbitTemplate.convertSendAndReceive("carModel.get", "", userId);
        try {
            carModelDto = new ObjectMapper().readValue(messageObject, CarModelDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return carModelDto;
    }

    public List<CarModelDto> getAllCarModelMessage() {
        List<CarModelDto> carModelDtoList = null;
        String messageObject = (String) rabbitTemplate.convertSendAndReceive("allCarModel.get", "", "");
        try {
            ObjectMapper mapper = new ObjectMapper();
            carModelDtoList = mapper.readValue(messageObject, mapper.getTypeFactory().constructCollectionType(List.class, CarModelDto.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return carModelDtoList;
    }

    public EngineDto getEngineMessage(Long userId) {
        EngineDto engineDto = null;
        String messageObject = (String) rabbitTemplate.convertSendAndReceive("engine.get", "", userId);
        try {
            engineDto = new ObjectMapper().readValue(messageObject, EngineDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return engineDto;
    }

    public List<EngineDto> getAllEngineMessage() {
        List<EngineDto> engineDtoList = null;
        String messageObject = (String) rabbitTemplate.convertSendAndReceive("allEngine.get", "", "");
        try {
            ObjectMapper mapper = new ObjectMapper();
            engineDtoList = mapper.readValue(messageObject, mapper.getTypeFactory().constructCollectionType(List.class, EngineDto.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return engineDtoList;
    }

    public void sendCarMarkDeleteMessage(Long userId) {
        rabbitTemplate.convertAndSend("carMark.delete", "", userId);
    }

    public void sendCarMarkMessage(CarMarkDto carMarkDto) {
        String exchangeName = "carMark";
        String messageString = null;
        try {
            messageString = new ObjectMapper().writeValueAsString(carMarkDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("[MESSAGE BROKER] Sending company info to exchange " + exchangeName + ": " + messageString);
        rabbitTemplate.convertAndSend(exchangeName, "", messageString);
    }

    public void sendEngineDeleteMessage(Long userId) {
        rabbitTemplate.convertAndSend("engine.delete", "", userId);
    }

    public void sendEngineMessage(EngineDto engineDto) {
        String exchangeName = "engine";
        String messageString = null;
        try {
            messageString = new ObjectMapper().writeValueAsString(engineDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("[MESSAGE BROKER] Sending company info to exchange " + exchangeName + ": " + messageString);
        rabbitTemplate.convertAndSend(exchangeName, "", messageString);
    }

    public void sendCarModelDeleteMessage(Long userId) {
        rabbitTemplate.convertAndSend("carModel.delete", "", userId);
    }

    public void sendCarModelMessage(CarModelDto carModelDto) {
        String exchangeName = "carModel";
        String messageString = null;
        try {
            messageString = new ObjectMapper().writeValueAsString(carModelDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("[MESSAGE BROKER] Sending company info to exchange " + exchangeName + ": " + messageString);
        rabbitTemplate.convertAndSend(exchangeName, "", messageString);
    }
}
