package com.lab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@NoArgsConstructor
public class CarMarkDto implements Serializable {
    private Long id;
    private String name;
    private Date releaseDate;
}
