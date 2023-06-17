package com.lab.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.controllers.CarModelController;
import com.lab.dto.CarMarkDto;
import com.lab.dto.CarModelDto;
import com.lab.entities.ExtraCarModel;
import com.lab.repository.CarModelRepository;
import com.lab.services.CarModelService;
import entities.CarMark;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class  CarModelControllerTest {
    private MockMvc mvc;

    @Mock
    private CarModelService carModelService;

    @InjectMocks
    private CarModelController carModelController;
    private JacksonTester<CarModelDto> jsonCarModelDto;
    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(carModelController)
                .build();
    }

    @Test
    public void addCarMark() throws Exception {
        var carMark = new CarMarkDto();
        carMark.setName("mark");
        carMark.setId(2L);
        carMark.setReleaseDate(new Date(100000));

        var carModel = new CarModelDto();
        carModel.setLength(10);
        carModel.setWidth(5);
        carModel.setBodyType(ExtraCarModel.Body_Type.SEDAN);
        carModel.setHeight(10);
        carModel.setName("car");
        carModel.setCarMark(carMark);

        MockHttpServletResponse response = mvc.perform(
                post("/carModel/add").contentType(MediaType.APPLICATION_JSON).content(
                        jsonCarModelDto.write(carModel).getJson()
                )).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void getCarMarkById() throws Exception {
        var carMark = new CarMarkDto();
        carMark.setId(2L);
        carMark.setName("car");
        carMark.setReleaseDate(new Date(100000));
        var carModel = new CarModelDto();
        carModel.setId(2L);
        carModel.setLength(10);
        carModel.setWidth(5);
        carModel.setBodyType(ExtraCarModel.Body_Type.SEDAN);
        carModel.setHeight(10);
        carModel.setName("car");
        carModel.setCarMark(carMark);

        given(carModelService.getById(2L))
                .willReturn(carModel);

        MockHttpServletResponse response = mvc.perform(
                        get("/carModel/find/2")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), jsonCarModelDto.write(carModel).getJson());
    }
}