package com.lab.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lab.dto.CarMarkDto;
import com.lab.repository.CarMarkRepository;
import com.lab.services.CarMarkService;
import entities.CarMark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
public class CarMarkController {
    private final CarMarkService carMarkService;

    @Autowired
    public CarMarkController(CarMarkService carMarkService) {
        this.carMarkService = carMarkService;
    }

    @PostMapping("/carMark/add")
    @PreAuthorize("hasRole('ADMIN')")
    public void add(@RequestBody CarMarkDto carMark) {
        carMarkService.save(carMark);
    }

    @GetMapping("/carMark/list")
    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<CarMarkDto> getAll() {
        return carMarkService.getAll();
    }

    @GetMapping("/carMark/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CarMarkDto findById(@PathVariable Long id) {
        return carMarkService.getById(id);
    }
    @DeleteMapping("/carMark/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id)
    {
        carMarkService.delete(id);
    }
}
