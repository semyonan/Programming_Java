package com.lab.services;

import com.lab.dto.CarModelDto;
import com.lab.dto.EngineDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EngineService {
    public EngineDto save(EngineDto engineDto);
    public EngineDto  getById(Long id);

    public List<EngineDto > getAll();

    public EngineDto update(EngineDto  engineDto);

    public void delete(Long id);
}
