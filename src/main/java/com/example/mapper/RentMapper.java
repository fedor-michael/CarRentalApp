package com.example.mapper;

import com.example.model.car.Car;
import com.example.model.rent.Rent;
import com.example.model.rent.RentDto;
import com.example.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RentMapper {

    RentMapper INSTANCE = Mappers.getMapper(RentMapper.class);

    @Mapping(source = "car", target = "carId", qualifiedByName = "carToCarId")
    @Mapping(source = "user", target = "userId", qualifiedByName = "userToUserId")
        // todo ignore?
    RentDto fromEntity(Rent rent);

    @Named("carToCarId")
    static long carToCarId(Car car) {
        return car.getId();
    }

    @Named("userToUserId")
    static long carToCarId(User user) {
        return user.getId();
    }

}
