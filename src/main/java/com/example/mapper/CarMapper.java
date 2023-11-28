package com.example.mapper;

import com.example.model.car.Car;
import com.example.model.car.CarDto;
import com.example.model.rent.Rent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mapping(source = "rents", target = "rentId", qualifiedByName = "rentsToRentIds")
    CarDto fromEntity(Car car);

    @Named("rentsToRentIds")
    static Set<Long> rentsToRentIds(Set<Rent> rents) {
        return rents.stream()
                .map(Rent::getId)
                .collect(Collectors.toSet());
    }

}
