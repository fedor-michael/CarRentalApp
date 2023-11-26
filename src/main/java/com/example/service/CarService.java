package com.example.service;

import com.example.model.car.CarDto;
import com.example.model.car.CreateCarCommand;
import com.example.model.car.UpdateCarCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {

    Page<CarDto> findAll(Pageable pageable);

    CarDto findById(Long id);

    CarDto save(CreateCarCommand command);

    CarDto updateCar(UpdateCarCommand command);

    void deleteCar(Long id);

    Page<CarDto> findNewerThan(Pageable pageable, int year);

    Page<CarDto> findAllFreeToRent(Pageable pageable);

}
