package com.lab.repository;

import entities.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EngineRepository extends JpaRepository<Engine, Long> {
    public List<Engine> getAllByCarModelId(Long id);
    public List<Engine> getAllByName(String name);
    Engine findEngineById(Long id);
}
