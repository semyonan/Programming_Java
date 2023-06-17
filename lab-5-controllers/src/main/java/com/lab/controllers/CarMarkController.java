package com.lab.controllers;

import com.lab.dto.CarMarkDto;
import com.lab.messagebroker.publisher.MessagePublisher;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CarMarkController {
    private final MessagePublisher messagePublisher;

    @PostMapping("/carMark/add")
    @PreAuthorize("hasRole('ADMIN')")
    public void add(@RequestBody CarMarkDto carMark) {
        messagePublisher.sendCarMarkMessage(carMark);
    }

    @GetMapping("/carMark/list")
    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<CarMarkDto> getAll() {
        return messagePublisher.getAllCarMarkMessage();
    }

    @GetMapping("/carMark/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CarMarkDto findById(@PathVariable Long id) {
        return messagePublisher.getCarMarkMessage(id);
    }
    @DeleteMapping("/carMark/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id)
    {
        messagePublisher.sendCarMarkDeleteMessage(id);
    }
}
