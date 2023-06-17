package com.lab.dto;

import com.lab.entities.ExtraCarModel;
import entities.CarMark;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CarModelDto implements Serializable {
    private Long id;
    private CarMarkDto carMark;
    private String name;
    private Integer length;
    private Integer width;
    private Integer height;
    private ExtraCarModel.Body_Type bodyType;

}
