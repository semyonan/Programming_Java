package com.lab.controllers;

import com.lab.dto.CarModelDto;
import com.lab.messagebroker.publisher.MessagePublisher;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CarModelController {
    private final MessagePublisher messagePublisher;

    @PostMapping("/carModel/add")
    @PreAuthorize("hasRole('ADMIN')")
    public void add(@RequestBody CarModelDto carModel) {
        messagePublisher.sendCarModelMessage(carModel);
    }

    @GetMapping("/carModel/list")
    @PreAuthorize("hasRole('USER')")
    public Iterable<CarModelDto > getAll() {

        return messagePublisher.getAllCarModelMessage();
    }

    @GetMapping("/carModel/find/{id}")
    @PreAuthorize("hasRole('USER')")
    public CarModelDto findById(@PathVariable Long id) {
        return messagePublisher.getCarModelMessage(id);
    }
    @DeleteMapping("/carModel/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id)
    {
        messagePublisher.sendCarModelDeleteMessage(id);
    }
}
