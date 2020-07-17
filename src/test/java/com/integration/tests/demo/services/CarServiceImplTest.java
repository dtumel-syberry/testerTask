package com.integration.tests.demo.services;

import com.integration.tests.demo.dtos.CarDTO;
import com.integration.tests.demo.entities.Car;
import com.integration.tests.demo.repositories.CarRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarServiceImplTest {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Test
    void addCar() {
        CarDTO carDTO = new CarDTO();
        carDTO.setName("BMW");

        carService.addCar(carDTO);

        List<Car> cars = carService.search(carDTO.getName(), null);
        assertEquals(cars.get(0).getName(), carDTO.getName());
    }

    @Test
    void searchByNameWhenExist() {
        String name = "BMW";

        Car testCar = new Car();
        testCar.setName(name);
        Car resultCar = carRepository.save(testCar);

        List<Car> cars = carService.search(name, null);
        assertTrue(cars.size() != 0);
        cars.forEach(car -> assertEquals(car.getName(), resultCar.getName()));
    }

    @Test
    void searchByNameWhenNotExist() {
        String name = "Reno";
        List<Car> cars = carService.search(name, null);
        assertEquals(0, cars.size());
    }
}