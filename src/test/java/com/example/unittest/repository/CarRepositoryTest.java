package com.example.unittest.repository;

import com.example.mapper.CarMapper;
import com.example.model.car.Car;
import com.example.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@DataJpaTest
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    private Car car1;
    private Car car2;
    private Pageable pageable;

    @BeforeEach
    public void init() {
        car1 = Car.builder()
                .vin("WBA12345671")
                .productionYear(2021)
                .brand("BMW")
                .model("M3")
                .mileage(20_000)
                .registration("S2 YBKI")
                .isAvailable(false)
                .build();

        car2 = Car.builder()
                .vin("WBA12345672")
                .productionYear(2015)
                .brand("BMW")
                .model("318d")
                .mileage(30_000)
                .registration("W0 OLNY")
                .isAvailable(true)
                .build();

        pageable = Pageable.unpaged();
    }

    @Test
    public void shouldReturnSavedEntity() {
        Car savedCar = carRepository.save(car1);

        assertNotNull(savedCar);
        assertEquals(car1.getId(), savedCar.getId());
        assertEquals(car1.getVin(), savedCar.getVin());
        assertEquals(car1.getProductionYear(), savedCar.getProductionYear());
        assertEquals(car1.getBrand(), savedCar.getBrand());
        assertEquals(car1.getModel(), savedCar.getModel());
        assertEquals(car1.getMileage(), savedCar.getMileage());
        assertEquals(car1.getRegistration(), savedCar.getRegistration());
        assertEquals(car1.getIsAvailable(), savedCar.getIsAvailable());
        assertEquals(Collections.emptySet(), savedCar.getRents());
    }

    @Test
    public void shouldReturnAllEntities() {
        carRepository.save(car1);
        carRepository.save(car2);

        List<Car> returnedAllCars = carRepository.findAll();

        assertNotNull(returnedAllCars);
        assertFalse(returnedAllCars.isEmpty());
        assertEquals(returnedAllCars.size(), 2);
        assertEquals(car1, returnedAllCars.stream()
                .filter(Objects::nonNull)
                .filter(v -> v.getId() == 1)
                .findFirst()
                .orElseThrow(EntityNotFoundException::new));
        assertEquals(car2, returnedAllCars.stream()
                .filter(Objects::nonNull)
                .filter(v -> v.getId() == 2)
                .findFirst()
                .orElseThrow(EntityNotFoundException::new));
    }

    @Test
    public void shouldReturnEntityWithId2() {
        carRepository.save(car1);
        carRepository.save(car2);

        Car returnedCar = Optional.ofNullable(carRepository.findById(2L)).get().orElseThrow(EntityNotFoundException::new);

        assertEquals(car2.getId(), returnedCar.getId());
        assertEquals(car2.getVin(), returnedCar.getVin());
        assertEquals(car2.getProductionYear(), returnedCar.getProductionYear());
        assertEquals(car2.getBrand(), returnedCar.getBrand());
        assertEquals(car2.getModel(), returnedCar.getModel());
        assertEquals(car2.getMileage(), returnedCar.getMileage());
        assertEquals(car2.getRegistration(), returnedCar.getRegistration());
        assertEquals(car2.getIsAvailable(), returnedCar.getIsAvailable());
        assertEquals(Collections.emptySet(), returnedCar.getRents());
    }

    @Test
    public void shouldReturnUpdatedEntityWithId1() {
        carRepository.save(car1);

        car1.setModel("M4");
        Car returnedCar = carRepository.saveAndFlush(car1);

        assertNotNull(returnedCar);
        assertEquals(car1.getId(), returnedCar.getId());
        assertEquals(car1.getVin(), returnedCar.getVin());
        assertEquals(car1.getProductionYear(), returnedCar.getProductionYear());
        assertEquals(car1.getBrand(), returnedCar.getBrand());
        assertEquals(car1.getModel(), returnedCar.getModel());
        assertEquals(car1.getMileage(), returnedCar.getMileage());
        assertEquals(car1.getRegistration(), returnedCar.getRegistration());
        assertEquals(car1.getIsAvailable(), returnedCar.getIsAvailable());
        assertEquals(Collections.emptySet(), returnedCar.getRents());
    }

    @Test
    public void shouldDeleteEntityWithId1() {
        carRepository.save(car1);

        int numberOfElementsInDB = (int) carRepository.count();
        carRepository.deleteById(1L);
        int numberOfElementsInDBAfterDelete = (int) carRepository.count();

        assertEquals(numberOfElementsInDB, 1);
        assertEquals(numberOfElementsInDBAfterDelete, 0);
    }

    @Test
    public void shouldReturnAllCarsNewerThan2020() {
        carRepository.save(car1);
        carRepository.save(car2);

        Car returnedCar = carRepository.findAllNewerThan(2020, pageable).getContent().get(0);

        assertNotNull(returnedCar);
        assertEquals(car1.getId(), returnedCar.getId());
        assertEquals(car1.getVin(), returnedCar.getVin());
        assertEquals(car1.getProductionYear(), returnedCar.getProductionYear());
        assertEquals(car1.getBrand(), returnedCar.getBrand());
        assertEquals(car1.getModel(), returnedCar.getModel());
        assertEquals(car1.getMileage(), returnedCar.getMileage());
        assertEquals(car1.getRegistration(), returnedCar.getRegistration());
        assertEquals(car1.getIsAvailable(), returnedCar.getIsAvailable());
        assertEquals(Collections.emptySet(), returnedCar.getRents());
    }

    @Test
    public void shouldReturnAllAvailableCars() {
        carRepository.save(car1);
        carRepository.save(car2);

        Car returnedCar = carRepository.findAllFreeToRent(pageable).getContent().get(0);

        assertNotNull(returnedCar);
        assertEquals(car2.getId(), returnedCar.getId());
        assertEquals(car2.getVin(), returnedCar.getVin());
        assertEquals(car2.getProductionYear(), returnedCar.getProductionYear());
        assertEquals(car2.getBrand(), returnedCar.getBrand());
        assertEquals(car2.getModel(), returnedCar.getModel());
        assertEquals(car2.getMileage(), returnedCar.getMileage());
        assertEquals(car2.getRegistration(), returnedCar.getRegistration());
        assertEquals(car2.getIsAvailable(), returnedCar.getIsAvailable());
        assertEquals(Collections.emptySet(), returnedCar.getRents());
    }

}
