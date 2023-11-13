package com.example.rent.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class RentDto {

    long id;
    LocalDate dateFrom;
    LocalDate dateTo;
    String status;
    long carId;
    long userId;
    int startMileage;
    int endMileage;

    public static RentDto fromEntity(Rent rent) {
        return RentDto.builder()
                .id(rent.getId())
                .dateFrom(rent.getDateFrom())
                .dateTo(rent.getDateTo())
                .status(rent.getStatus().toString())
                .carId(rent.getCar().getId())
                .userId(rent.getUser().getId())
                .startMileage(rent.getStartMileage())
                .endMileage(rent.getEndMileage())
                .build();
    }
}
