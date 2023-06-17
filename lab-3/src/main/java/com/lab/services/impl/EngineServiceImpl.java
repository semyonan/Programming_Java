package com.lab.services.impl;

import com.lab.dto.EngineDto;
import com.lab.entities.Engine;
import com.lab.entities.ExtraCarModel;
import com.lab.repository.CarMarkRepository;
import com.lab.repository.CarModelRepository;
import com.lab.repository.EngineRepository;
import com.lab.services.AccessService;
import com.lab.services.CarModelService;
import com.lab.services.EngineService;
import entities.CarModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EngineServiceImpl implements EngineService {

    private final EngineRepository engineRepository;
    private final CarModelRepository carModelRepository;
    private final AccessService accessService;
    private final ModelMapper modelMapper;

    @Autowired
    public EngineServiceImpl(ModelMapper modelMapper, EngineRepository engineRepository, AccessService accessService, CarModelRepository carModelRepository) {
        this.modelMapper = modelMapper;
        this.accessService = accessService;
        this.carModelRepository = carModelRepository;
        this.engineRepository = engineRepository;
    }
    @Override
    public EngineDto save(EngineDto engineDto) {
        var engine = modelMapper.map(engineDto, Engine.class);
        var savedEngine = engineRepository.save(engine);

        var savedEngineDto = modelMapper.map(savedEngine , EngineDto.class);

        return savedEngineDto;
    }

    @Override
    public EngineDto getById(Long id) {
        if (accessService.userHasAccess(engineRepository.findById(id).get().getCarModel().getCarMark().getId())) {
            throw new SecurityException("User has no access");
        }
        var engine = engineRepository.findById(id).get();
        return modelMapper.map(engine, EngineDto.class);
    }

    @Override
    public List<EngineDto> getAll() {
        var userId = accessService.getUserId();
        List<ExtraCarModel> carModels = carModelRepository.getAllByCarMarkId(userId);
        List<Engine> engines = new ArrayList<>();
        for (var carModel:carModels) {
            engines.addAll(engineRepository.getAllByCarModelId(carModel.getId()));
        }
        return engines.stream().map((engine) -> modelMapper.map(engine, EngineDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EngineDto update(EngineDto engineDto) {
        var existingEngine = engineRepository.findById(engineDto.getId()).get();
        existingEngine.setHeight(engineDto.getHeight());
        existingEngine.setCarModel(modelMapper.map(engineDto.getCarModel(), ExtraCarModel.class));
        existingEngine.setName(engineDto.getName());
        existingEngine.setCapacity(engineDto.getCapacity());
        existingEngine.setNumberOfCylinders(engineDto.getNumberOfCylinders());
        var updatedEngine = engineRepository.save(existingEngine);
        return modelMapper.map(updatedEngine, EngineDto.class);
    }

    @Override
    public void delete(Long id) {
        engineRepository.deleteById(id);
    }
}
