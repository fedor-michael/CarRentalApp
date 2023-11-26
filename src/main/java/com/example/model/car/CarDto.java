package com.example.model.car;

import com.example.model.rent.Rent;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.stream.Collectors;

@Value
@Builder
public class CarDto {

    long id;
    String vin;
    int productionYear;
    String brand;
    String model;
    int mileage;
    String registration;
    boolean isAvailable;
    Set<Long> rentId;

    public static CarDto fromEntity(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .vin(car.getVin())
                .productionYear(car.getProductionYear())
                .brand(car.getBrand())
                .model(car.getModel())
                .mileage(car.getMileage())
                .registration(car.getRegistration())
                .isAvailable(car.getIsAvailable())
                .rentId(car.getRents()
                        .stream()
                        .map(Rent::getId)
                        .collect(Collectors.toSet()))
                .build();
    }

}
