package com.integration.tests.demo.repositories;

import com.integration.tests.demo.entities.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    public void testCreateCar() {
        Car car = new Car();
        car.setId((long) 1);
        car.setName("BMW");

        Car savedCar = carRepository.save(car);

        assertNotNull(savedCar);
        assertEquals(car.getId(), savedCar.getId());
        assertEquals(car.getName(), savedCar.getName());
    }


    @Test
    public void testFindCarsByNameExist() {
        String name = "BMW";
        List<Car> cars = carRepository.findCarsByName(name);
        assertEquals(1, cars.size());
        assertEquals(cars.get(0).getName(), name);
    }

    @Test
    public void testFindCarsByNameNotExist() {
        String name = "Reno";
        List<Car> cars = carRepository.findCarsByName(name);
        assertEquals(0, cars.size());
    }
}