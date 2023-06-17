package com.lab.controllers;

import com.lab.dto.EngineDto;
import com.lab.messagebroker.publisher.MessagePublisher;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class EngineController {

    private final MessagePublisher messagePublisher;

    @PostMapping("/engine/add")
    @PreAuthorize("hasRole('ADMIN')")
    public void add(@RequestBody EngineDto engine) {
        messagePublisher.sendEngineMessage(engine);
    }

    @GetMapping("/engine/list")
    @PreAuthorize("hasRole('USER')")
    public Iterable<EngineDto> getAll() {

        return messagePublisher.getAllEngineMessage();
    }

    @GetMapping("/engine/find/{id}")
    @PreAuthorize("hasRole('USER')")
    public EngineDto findById(@PathVariable Long id) {
        return messagePublisher.getEngineMessage(id);
    }
    @DeleteMapping("/engine/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id)
    {
        messagePublisher.sendEngineDeleteMessage(id);
    }
}
