package com.lab.services;

import com.lab.dto.CarMarkDto;
import entities.CarMark;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarMarkService {
    public CarMarkDto save(CarMarkDto carMarkDto);
    public CarMarkDto getById(Long id);

    public List<CarMarkDto> getAll();

    public CarMarkDto update(CarMarkDto carMarkDto);

    public void delete(Long id);
}
