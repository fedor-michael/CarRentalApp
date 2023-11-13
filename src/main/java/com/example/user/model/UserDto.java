package com.example.user.model;

import com.example.rent.model.Rent;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.stream.Collectors;

@Value
@Builder
public class UserDto {

    long id;
    String name;
    String surname;
    String personId;
    long phoneNumber;
    String email;
    Set<Long> rentId;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .personId(user.getPersonId())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .rentId(user.getRents()
                        .stream()
                        .map(Rent::getId)
                        .collect(Collectors.toSet()))
                .build();
    }

}
