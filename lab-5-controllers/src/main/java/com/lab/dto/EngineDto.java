package com.lab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class EngineDto implements Serializable {

    private Long id;
    private CarModelDto carModel;
    private String name;
    private Integer numberOfCylinders;
    private Integer capacity;
    private Integer height;

}
