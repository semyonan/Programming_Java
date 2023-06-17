package com.lab.services;

import com.lab.dto.CarModelDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarModelService {
    public CarModelDto save(CarModelDto carModelDto);
    public CarModelDto  getById(Long id);

    public List<CarModelDto > getAll();

    public CarModelDto update(CarModelDto  carModelDto);

    public void delete(Long id);
}
