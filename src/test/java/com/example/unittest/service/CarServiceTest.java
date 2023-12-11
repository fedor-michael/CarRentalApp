package com.example.unittest.service;

import com.example.mapper.CarMapper;
import com.example.model.car.Car;
import com.example.model.car.CarDto;
import com.example.repository.CarRepository;
import com.example.repository.RentRepository;
import com.example.service.impl.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CarServiceTest {

    private Car car1;
    private Pageable pageable;

    @Mock
    private CarRepository carRepository;

    @Mock
    private RentRepository rentRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        carService = new CarService(carRepository, rentRepository);

        car1 = Car.builder()
                .vin("WBA12345671")
                .productionYear(2021)
                .brand("BMW")
                .model("M3")
                .mileage(20_000)
                .registration("S2 YBKI")
                .isAvailable(false)
                .build();

        pageable = Pageable.unpaged();
    }

    // Test .findAll()
    @Test
    public void shouldReturnEmptyPage() {
        when(carRepository.findAll(pageable)).thenReturn(Page.empty());

        Page<CarDto> result = carService.findAll(pageable);

        assertNotNull(result);
        assertEquals(0, result.getContent().size());
    }

    // Test .findById()
    @Test
    public void shouldReturnCarEntityWhenFindById1() {
        Long carId = 1L;
        when(carRepository.findById(carId)).thenReturn(Optional.of(car1));

        CarDto returnedCarDto = carService.findById(carId);

        assertNotNull(returnedCarDto);
        assertEquals(car1.getVin(), returnedCarDto.getVin());
        assertEquals(car1.getProductionYear(), Optional.of(returnedCarDto.getProductionYear()).get());
        assertEquals(car1.getBrand(), returnedCarDto.getBrand());
        assertEquals(car1.getModel(), returnedCarDto.getModel());
        assertEquals(car1.getMileage(), Optional.of(returnedCarDto.getMileage()).get());
        assertEquals(car1.getRegistration(), returnedCarDto.getRegistration());
        assertEquals(car1.getIsAvailable(), returnedCarDto.isAvailable());
        assertEquals(Collections.emptySet(), returnedCarDto.getRentId());
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenFindNotExistingId5() {
        Long carId = 1L;
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(com.example.exception.EntityNotFoundException.class,
                () -> carRepository.findById(555L)
                        .map(CarMapper.INSTANCE::fromEntity)
                        .orElseThrow(() -> new com.example.exception.EntityNotFoundException(Car.class.getSimpleName(), 555)));
    }

    // Test .save()
    @Test
    public void shouldReturnSavedEntity() {
        Long carId = 1L;

        when(carRepository.findById(carId)).thenReturn(Optional.of(car1));

        CarDto returnedCarDto = carService.findById(carId);

        assertNotNull(returnedCarDto);
        assertEquals(car1.getVin(), returnedCarDto.getVin());
        assertEquals(car1.getProductionYear(), Optional.of(returnedCarDto.getProductionYear()).get());
        assertEquals(car1.getBrand(), returnedCarDto.getBrand());
        assertEquals(car1.getModel(), returnedCarDto.getModel());
        assertEquals(car1.getMileage(), Optional.of(returnedCarDto.getMileage()).get());
        assertEquals(car1.getRegistration(), returnedCarDto.getRegistration());
        assertEquals(car1.getIsAvailable(), returnedCarDto.isAvailable());
        assertEquals(Collections.emptySet(), returnedCarDto.getRentId());
    }

}
