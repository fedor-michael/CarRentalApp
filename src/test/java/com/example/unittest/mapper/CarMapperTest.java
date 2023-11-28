package com.example.unittest.mapper;

import com.example.mapper.CarMapper;
import com.example.model.car.Car;
import com.example.model.car.CarDto;
import com.example.model.rent.Rent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CarMapperTest {

    private final CarMapper carMapper = CarMapper.INSTANCE;
    private Car car;

    @BeforeEach
    public void init() {
        car = Car.builder()
                .id(1L)
                .vin("WBA12345671")
                .productionYear(2015)
                .brand("BMW")
                .model("M3")
                .mileage(20_000)
                .registration("S2 YBKI")
                .isAvailable(true)
                .build();
    }

    @Test
    public void shouldReturnCarDtoWithEmptyRentsWhenPassingCarWithoutRents() {
        CarDto carDtoToTest = carMapper.fromEntity(car);

        assertNotNull(carDtoToTest);
        assertEquals(car.getId(), Optional.of(carDtoToTest.getId()).get());
        assertEquals(car.getVin(), carDtoToTest.getVin());
        assertEquals(car.getProductionYear(), Optional.of(carDtoToTest.getProductionYear()).get());
        assertEquals(car.getBrand(), carDtoToTest.getBrand());
        assertEquals(car.getModel(), carDtoToTest.getModel());
        assertEquals(car.getMileage(), Optional.of(carDtoToTest.getMileage()).get());
        assertEquals(car.getRegistration(), carDtoToTest.getRegistration());
        assertEquals(car.getIsAvailable(), carDtoToTest.isAvailable());
        assertEquals(Collections.emptySet(), carDtoToTest.getRentId());

    }

    @Test
    public void shouldReturnCarDtoWithRentsWhenPassingCarWithRents() {
        Rent rent = Rent.builder()
                .id(1L)
                .build();
        car.getRents().add(rent);
        CarDto carDtoToTest = carMapper.fromEntity(car);

        assertNotNull(carDtoToTest);
        assertEquals(car.getId(), Optional.of(carDtoToTest.getId()).get());
        assertEquals(car.getVin(), carDtoToTest.getVin());
        assertEquals(car.getProductionYear(), Optional.of(carDtoToTest.getProductionYear()).get());
        assertEquals(car.getBrand(), carDtoToTest.getBrand());
        assertEquals(car.getModel(), carDtoToTest.getModel());
        assertEquals(car.getMileage(), Optional.of(carDtoToTest.getMileage()).get());
        assertEquals(car.getRegistration(), carDtoToTest.getRegistration());
        assertEquals(car.getIsAvailable(), carDtoToTest.isAvailable());
        assertEquals(car.getRents()
                        .stream()
                        .map(Rent::getId)
                        .collect(Collectors.toSet()),
                carDtoToTest.getRentId());

    }

    @Test
    public void shouldReturnNullWhenPassingNull() {
        CarDto carDtoToTest = carMapper.fromEntity(null);
        assertNull(carDtoToTest);
    }

    @Test
    public void shouldReturnDtoWhenNullInRents() {
        car.getRents().add(null);
        CarDto carDtoToTest = carMapper.fromEntity(car);

        assertNotNull(carDtoToTest);
        assertEquals(car.getId(), Optional.of(carDtoToTest.getId()).get());
        assertEquals(car.getVin(), carDtoToTest.getVin());
        assertEquals(car.getProductionYear(), Optional.of(carDtoToTest.getProductionYear()).get());
        assertEquals(car.getBrand(), carDtoToTest.getBrand());
        assertEquals(car.getModel(), carDtoToTest.getModel());
        assertEquals(car.getMileage(), Optional.of(carDtoToTest.getMileage()).get());
        assertEquals(car.getRegistration(), carDtoToTest.getRegistration());
        assertEquals(car.getIsAvailable(), carDtoToTest.isAvailable());
        assertEquals(Collections.emptySet(), carDtoToTest.getRentId());

    }

    @Test
    public void shouldReturnDtoWhenRentsIsNull() {
        car.setRents(null);
        CarDto carDtoToTest = carMapper.fromEntity(car);

        assertNotNull(carDtoToTest);
        assertEquals(car.getId(), Optional.of(carDtoToTest.getId()).get());
        assertEquals(car.getVin(), carDtoToTest.getVin());
        assertEquals(car.getProductionYear(), Optional.of(carDtoToTest.getProductionYear()).get());
        assertEquals(car.getBrand(), carDtoToTest.getBrand());
        assertEquals(car.getModel(), carDtoToTest.getModel());
        assertEquals(car.getMileage(), Optional.of(carDtoToTest.getMileage()).get());
        assertEquals(car.getRegistration(), carDtoToTest.getRegistration());
        assertEquals(car.getIsAvailable(), carDtoToTest.isAvailable());
        assertEquals(Collections.emptySet(), carDtoToTest.getRentId());

    }

}
