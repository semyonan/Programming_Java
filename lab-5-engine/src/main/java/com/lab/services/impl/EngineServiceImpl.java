package com.lab.services.impl;

import com.lab.dto.EngineDto;
import entities.Engine;
import entities.ExtraCarModel;
import com.lab.repository.EngineRepository;
import com.lab.services.AccessService;
import com.lab.services.EngineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EngineServiceImpl implements EngineService {

    private final EngineRepository engineRepository;
    private final AccessService accessService;
    private final ModelMapper modelMapper;

    @Autowired
    public EngineServiceImpl(ModelMapper modelMapper, EngineRepository engineRepository, AccessService accessService) {
        this.modelMapper = modelMapper;
        this.accessService = accessService;
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
        if (!accessService.userHasAccess(engineRepository.findById(id).get().getCarModel().getCarMark().getId())) {
            throw new SecurityException("User has no access");
        }
        var engine = engineRepository.findById(id).get();
        return modelMapper.map(engine, EngineDto.class);
    }

    @Override
    public List<EngineDto> getAll() {
        List<Engine> allEngines = engineRepository.findAll();
        List<Engine> engines = new ArrayList<>();
        for (var engine:allEngines) {
            System.out.println(accessService.getUserId());
            System.out.println(engineRepository.findById(engine.getId()).get().getCarModel().getCarMark().getId());
            if (accessService.userHasAccess(engineRepository.findById(engine.getId()).get().getCarModel().getCarMark().getId())) {
                System.out.println("Aa");
                engines.add(engine);
            }
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
