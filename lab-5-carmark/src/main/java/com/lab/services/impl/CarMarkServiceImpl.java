package com.lab.services.impl;


import com.lab.dto.RegisterRequest;
import com.lab.messagebroker.publisher.MessagePublisher;
import com.lab.dto.CarMarkDto;
import com.lab.repository.CarMarkRepository;
import com.lab.services.CarMarkService;
import entities.CarMark;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CarMarkServiceImpl implements CarMarkService {
    private final CarMarkRepository carMarkRepository;
    private final ModelMapper modelMapper;
    private final MessagePublisher messagePublisher;

    @Autowired
    public CarMarkServiceImpl(ModelMapper modelMapper, CarMarkRepository carMarkRepository, MessagePublisher messagePublisher) {
        this.modelMapper = modelMapper;
        this.carMarkRepository = carMarkRepository;
        this.messagePublisher = messagePublisher;
    }

    @Override
    public CarMarkDto save(CarMarkDto carMarkDto) {
        var carMark = modelMapper.map(carMarkDto, CarMark.class);
        var savedCarMark = carMarkRepository.save(carMark);

        messagePublisher.sendRegisterMessage(new RegisterRequest(savedCarMark.getId().toString(), savedCarMark.getName()+savedCarMark.getId().toString(), "ROLE_USER", modelMapper.map(savedCarMark, CarMarkDto.class)));
        System.out.println(savedCarMark.getName()+savedCarMark.getId().toString());
        var savedCarMarkDto = modelMapper.map(savedCarMark, CarMarkDto.class);

        return savedCarMarkDto;
    }

    @Override
    public CarMarkDto getById(Long id) {
        CarMark carMark = carMarkRepository.findById(id).get();
        return modelMapper.map(carMark, CarMarkDto.class);
    }

    @Override
    public List<CarMarkDto> getAll() {
        List<CarMark> carMarks = carMarkRepository.findAll();

        return carMarks.stream().map((carMark) -> modelMapper.map(carMark, CarMarkDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CarMarkDto update(CarMarkDto carMarkDto) {
        CarMark existingCarMark = carMarkRepository.findById(carMarkDto.getId()).get();
        existingCarMark.setName(carMarkDto.getName());
        existingCarMark.setReleaseDate(carMarkDto.getReleaseDate());
        CarMark updatedCarMark = carMarkRepository.save(existingCarMark);
        return modelMapper.map(updatedCarMark, CarMarkDto.class);
    }

    @Override
    public void delete(Long id) {
        carMarkRepository.deleteById(id);
    }

}
