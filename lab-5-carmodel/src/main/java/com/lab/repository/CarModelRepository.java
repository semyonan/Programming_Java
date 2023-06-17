package com.lab.repository;

import entities.ExtraCarModel;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelRepository extends JpaRepository<ExtraCarModel, Long> {
    public List<ExtraCarModel> getAllByCarMarkId(Long id);
    public List<ExtraCarModel> getAllByName(String name);

    ExtraCarModel findCarModelById(Long id);
}
