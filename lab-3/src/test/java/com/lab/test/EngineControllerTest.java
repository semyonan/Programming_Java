package com.lab.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.controllers.EngineController;
import com.lab.dto.CarMarkDto;
import com.lab.dto.CarModelDto;
import com.lab.dto.EngineDto;
import com.lab.entities.Engine;
import com.lab.entities.ExtraCarModel;
import com.lab.repository.EngineRepository;
import com.lab.services.EngineService;
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
class  EngineControllerTest {
    private MockMvc mvc;

    @Mock
    private EngineService engineService;

    @InjectMocks
    private EngineController engineController;
    private JacksonTester<EngineDto> jsonEngineDto;
    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(engineController)
                .build();
    }

    @Test
    public void addCarMark() throws Exception {
        var carMark = new CarMarkDto();
        carMark.setName("mark");
        carMark.setId(2L);
        carMark.setReleaseDate(new Date(100000));

        var carModel = new CarModelDto();
        carMark.setId(2L);
        carModel.setLength(10);
        carModel.setWidth(5);
        carModel.setBodyType(ExtraCarModel.Body_Type.SEDAN);
        carModel.setHeight(10);
        carModel.setName("car");
        carModel.setCarMark(carMark);

        var engine = new EngineDto();
        engine.setNumberOfCylinders(10);
        engine.setCapacity(20);
        engine.setName("engine");
        engine.setHeight(20);
        engine.setCarModel(carModel);

        MockHttpServletResponse response = mvc.perform(
                post("/engine/add").contentType(MediaType.APPLICATION_JSON).content(
                        jsonEngineDto.write(engine).getJson()
                )).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void getCarMarkById() throws Exception {
        var carMark = new CarMarkDto();
        carMark.setName("mark");
        carMark.setId(2L);
        carMark.setReleaseDate(new Date(100000));

        var carModel = new CarModelDto();
        carMark.setId(2L);
        carModel.setLength(10);
        carModel.setWidth(5);
        carModel.setBodyType(ExtraCarModel.Body_Type.SEDAN);
        carModel.setHeight(10);
        carModel.setName("car");
        carModel.setCarMark(carMark);

        var engine = new EngineDto();
        engine.setId(2L);
        engine.setNumberOfCylinders(10);
        engine.setCapacity(20);
        engine.setName("engine");
        engine.setHeight(20);
        engine.setCarModel(carModel);

        given(engineService.getById(2L))
                .willReturn(engine);

        MockHttpServletResponse response = mvc.perform(
                        get("/engine/find/2")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), jsonEngineDto.write(engine).getJson());
    }
}
