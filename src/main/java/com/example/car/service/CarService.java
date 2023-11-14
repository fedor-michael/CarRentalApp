package com.example.car.service;

import com.example.car.model.CarDto;
import com.example.car.model.CreateCarCommand;
import com.example.car.model.UpdateCarCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {

    Page<CarDto> findAll(Pageable pageable);

    CarDto findById(Long id);

    CarDto save(CreateCarCommand command);

    CarDto updateCar(UpdateCarCommand command);

    void deleteCar(Long id);

}
