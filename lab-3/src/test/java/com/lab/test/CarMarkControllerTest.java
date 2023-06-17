package com.lab.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.controllers.CarMarkController;
import com.lab.dto.CarMarkDto;
import com.lab.repository.CarMarkRepository;
import com.lab.services.CarMarkService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class  CarMarkControllerTest {
    private MockMvc mvc;

    @Mock
    private CarMarkService carMarkService;

    @InjectMocks
    private CarMarkController carMarkController;

    private JacksonTester<CarMarkDto> jsonCarMarkDto;
    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(carMarkController)
                .build();
    }

    @Test
    public void addCarMark() throws Exception {
        var carMark = new CarMarkDto();
        carMark.setName("car");
        carMark.setReleaseDate(new Date(100000));
        var json = jsonCarMarkDto.write(carMark).getJson();

        MockHttpServletResponse response = mvc.perform(
                post("/carMark/add").contentType(MediaType.APPLICATION_JSON).content(
                        jsonCarMarkDto.write(carMark).getJson()
                )).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void getCarMarkById() throws Exception {
        var carMark = new CarMarkDto();
        carMark.setId(2L);
        carMark.setName("car");
        carMark.setReleaseDate(new Date(100000));

        given(carMarkService.getById(2L))
                .willReturn(carMark);

        MockHttpServletResponse response = mvc.perform(
                        get("/carMark/find/2")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(response.getContentAsString(), jsonCarMarkDto.write(carMark).getJson());
        assertEquals(response.getStatus(), HttpStatus.OK.value());

    }
}
