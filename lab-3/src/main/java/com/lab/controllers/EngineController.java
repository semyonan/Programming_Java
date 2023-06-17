package com.lab.controllers;

import com.lab.dto.EngineDto;
import com.lab.entities.ExtraCarModel;
import com.lab.entities.Engine;
import com.lab.repository.EngineRepository;
import com.lab.services.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class EngineController {

    private final EngineService engineService;

    @Autowired
    public EngineController(EngineService engineService) {
        this.engineService = engineService;
    }

    @PostMapping("/engine/add")
    @PreAuthorize("hasRole('ADMIN')")
    public void add(@RequestBody EngineDto engine) {
        engineService.save(engine);
    }

    @GetMapping("/engine/list")
    @PreAuthorize("hasRole('USER')")
    public Iterable<EngineDto> getAll() {
        return engineService.getAll();
    }

    @GetMapping("/engine/find/{id}")
    @PreAuthorize("hasRole('USER')")
    public EngineDto findById(@PathVariable Long id) {
        return engineService.getById(id);
    }
    @DeleteMapping("/engine/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id)
    {
        engineService.delete(id);
    }
}
