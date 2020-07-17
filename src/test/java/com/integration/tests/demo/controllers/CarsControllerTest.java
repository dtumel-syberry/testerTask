package com.integration.tests.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.tests.demo.dtos.CarDTO;
import com.integration.tests.demo.entities.Car;
import com.integration.tests.demo.repositories.CarRepository;
import com.integration.tests.demo.services.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    @Test
    public void addCar() throws Exception {
        CarDTO carDTO = new CarDTO();
        carDTO.setName("BMW");

        MvcResult result = this.mockMvc.perform( MockMvcRequestBuilders
                .post("/cars/addCar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapToJson(carDTO)))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void searchCar() throws Exception {
        Car car = new Car();
        car.setId((long) 1);
        car.setName("BMW");

        Car savedCar = carRepository.save(car);

        List<Car> listCars = new ArrayList<Car>();
        listCars.add(savedCar);

        String inputInJson = this.mapToJson(listCars);
        MvcResult result = this.mockMvc.perform( MockMvcRequestBuilders
                .get("/cars/search")
                .param("name", "BMW"))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();

        String responseResult = response.getContentAsString();

        assertEquals(responseResult, inputInJson);
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}