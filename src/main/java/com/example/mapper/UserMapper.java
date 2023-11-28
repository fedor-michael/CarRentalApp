package com.example.mapper;

import com.example.model.rent.Rent;
import com.example.model.user.User;
import com.example.model.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "rents", target = "rentId", qualifiedByName = "rentsToRentIds")
    UserDto fromEntity(User user);

    @Named("rentsToRentIds")
    static Set<Long> rentsToRentIds(Set<Rent> rents) {
        return rents.stream()
                .map(Rent::getId)
                .collect(Collectors.toSet());
    }

}
