package com.lab.repository;

import entities.CarMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarMarkRepository extends JpaRepository<CarMark, Long> {
    public List<CarMark> getAllByName(String name);

    CarMark findCarMarkById(Long id);
}
