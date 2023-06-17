package com.lab.controllers;

import com.lab.dto.CarModelDto;
import com.lab.entities.ExtraCarModel;
import com.lab.repository.CarModelRepository;
import com.lab.services.CarModelService;
import entities.CarMark;
import com.lab.entities.ExtraCarModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
@RestController
public class CarModelController {
    private final CarModelService carModelService;

    @Autowired
    public CarModelController(CarModelService carModelService) {
        this.carModelService = carModelService;
    }

    @PostMapping("/carModel/add")
    @PreAuthorize("hasRole('ADMIN')")
    public void add(@RequestBody CarModelDto carModel) {
        carModelService.save(carModel);
    }

    @GetMapping("/carModel/list")
    @PreAuthorize("hasRole('USER')")
    public Iterable<CarModelDto > getAll() {
        return carModelService.getAll();
    }

    @GetMapping("/carModel/find/{id}")
    @PreAuthorize("hasRole('USER')")
    public CarModelDto findById(@PathVariable Long id) {
        return carModelService.getById(id);
    }
    @DeleteMapping("/carModel/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id)
    {
        carModelService.delete(id);
    }
}
