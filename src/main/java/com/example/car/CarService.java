package com.example.car;

import com.example.car.model.Car;
import com.example.car.model.CarDto;
import com.example.car.model.CreateCarCommand;
import com.example.car.model.UpdateCarCommand;
import com.example.exception.EntityNotFoundException;
import com.example.rent.RentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {

    private final CarRepository carRepository;
    private final RentRepository rentRepository;

    @Transactional(readOnly = true)
    public Page<CarDto> findAll(Pageable pageable) {
        return carRepository.findAll(pageable).map(CarDto::fromEntity);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public CarDto findById(Long id) {
        return carRepository.findById(id)
                .map(CarDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(Car.class.getSimpleName(), id));
    }

    // todo
    //findNewerThanYear
    // findWithMileageLessThan
    // findAvailable - zwraca liste lub seta

    public CarDto save(CreateCarCommand command) {
        Car savedCar = carRepository.save(Car.builder()
                .vin(command.getVin())
                .productionYear(command.getProductionYear())
                .brand(command.getBrand())
                .model(command.getModel())
                .mileage(command.getMileage())
                .registration(command.getRegistration())
                .isAvailable(command.getIsAvailable())
                .rents(Optional.ofNullable(command.getRentsId())
                        .orElseGet(Collections::emptySet)
                        .stream()
                        .filter(Objects::nonNull)
                        .map(rentRepository::findById)
                        .flatMap(Optional::stream)
                        .collect(Collectors.toSet()))
                .build());
        return CarDto.fromEntity(savedCar);
    }

    public CarDto updateCar(UpdateCarCommand command) {
        Car carToUpdate = carRepository.findById(command.getId())
                .map(car -> {
                    car.setVin(command.getVin());
                    car.setProductionYear(command.getProductionYear());
                    car.setBrand(command.getBrand());
                    car.setModel(command.getModel());
                    car.setMileage(command.getMileage());
                    car.setRegistration(command.getRegistration());
                    car.setIsAvailable(command.getIsAvailable());
                    return car;
                }).orElseThrow(() -> new EntityNotFoundException(Car.class.getSimpleName(), command.getId()));
        return CarDto.fromEntity(carRepository.saveAndFlush(carToUpdate));
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

}