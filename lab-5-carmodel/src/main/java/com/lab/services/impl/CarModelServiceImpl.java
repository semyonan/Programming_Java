package com.lab.services.impl;

import com.lab.dto.CarModelDto;
import entities.ExtraCarModel;
import com.lab.repository.CarModelRepository;
import com.lab.services.AccessService;
import com.lab.services.CarModelService;
import entities.CarMark;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarModelServiceImpl implements CarModelService {

    private final CarModelRepository carModelRepository;

    private final AccessService accessService;
    private final ModelMapper modelMapper;

    @Autowired
    public CarModelServiceImpl(ModelMapper modelMapper, CarModelRepository carModelRepository, AccessService accessService) {
        this.modelMapper = modelMapper;
        this.accessService = accessService;
        this.carModelRepository = carModelRepository;
    }
    @Override
    public CarModelDto save(CarModelDto carModelDto) {
        var carModel = modelMapper.map(carModelDto, ExtraCarModel.class);
        var savedCarModel = carModelRepository.save(carModel);

        var savedCarModelDto = modelMapper.map(savedCarModel, CarModelDto.class);

        return savedCarModelDto;
    }

    @Override
    public CarModelDto getById(Long id) {
        if (!accessService.userHasAccess(carModelRepository.findById(id).get().getCarMark().getId())) {
            throw new SecurityException("User has no access");
        }
        var carModel = carModelRepository.findById(id).get();
        return modelMapper.map(carModel, CarModelDto.class);
    }

    @Override
    public List<CarModelDto> getAll() {
        List<ExtraCarModel> carModels = carModelRepository.getAllByCarMarkId(accessService.getUserId());
        return carModels.stream().map((carModel) -> modelMapper.map(carModel, CarModelDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CarModelDto update(CarModelDto carModelDto) {
        if (accessService.userHasAccess(carModelDto.getId())) {
            throw new RuntimeException("User has no access");
        }
        var existingCarModel = carModelRepository.findById(carModelDto.getId()).get();
        existingCarModel.setHeight(carModelDto.getHeight());
        existingCarModel.setBodyType(carModelDto.getBodyType());
        existingCarModel.setCarMark(modelMapper.map(carModelDto.getCarMark(), CarMark.class));
        existingCarModel.setWidth(carModelDto.getWidth());
        existingCarModel.setLength(carModelDto.getLength());
        existingCarModel.setName(carModelDto.getName());
        var updatedCarModel = carModelRepository.save(existingCarModel);
        return modelMapper.map(updatedCarModel, CarModelDto.class);
    }

    @Override
    public void delete(Long id) {
        carModelRepository.deleteById(id);
    }
}
