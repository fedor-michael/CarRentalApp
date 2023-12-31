package com.example.service.impl;

import com.example.exception.EntityNotFoundException;
import com.example.imports.exception.InvalidPersonIDException;
import com.example.mapper.UserMapper;
import com.example.repository.RentRepository;
import com.example.repository.UserRepository;
import com.example.model.user.CreateUserCommand;
import com.example.model.user.UpdateUserCommand;
import com.example.model.user.User;
import com.example.model.user.UserDto;
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
public class UserService implements com.example.service.UserService {

    private final UserRepository userRepository;
    private final RentRepository rentRepository;

    @Transactional(readOnly = true)
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserMapper.INSTANCE::fromEntity);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper.INSTANCE::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), id));
    }

    public UserDto save(CreateUserCommand command) {
        User savedUser = userRepository.save(User.builder()
                .name(command.getName())
                .surname(command.getSurname())
                .personId(checkPersonIDUniquenessAndGetUniquePersonID(command.getPersonId()))
                .phoneNumber(command.getPhoneNumber())
                .email(command.getEmail())
                .rents(Optional.ofNullable(command.getRentID())
                        .orElseGet(Collections::emptySet)
                        .stream()
                        .filter(Objects::nonNull)
                        .map(rentRepository::findById)
                        .flatMap(Optional::stream)
                        .collect(Collectors.toSet()))
                .build());
        return UserMapper.INSTANCE.fromEntity(savedUser);
    }

    public UserDto updateUser(UpdateUserCommand command) {
        User userToUpdate = userRepository.findById(command.getId())
                .map(user -> {
                    user.setName(command.getName());
                    user.setSurname(command.getSurname());
                    user.setPersonId(checkPersonIDUniquenessAndGetUniquePersonID(command.getPersonId()));
                    user.setPhoneNumber(command.getPhoneNumber());
                    user.setEmail(command.getEmail());
                    return user;
                }).orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), command.getId()));
        return UserMapper.INSTANCE.fromEntity(userRepository.saveAndFlush(userToUpdate));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private String checkPersonIDUniquenessAndGetUniquePersonID(String personId) {
        userRepository.findAll()
                .stream()
                .map(User::getPersonId)
                .filter(v -> v.equals(personId))
                .findFirst()
                .ifPresent(v -> {
                    throw new InvalidPersonIDException(personId);
                });
        return personId;
    }

}
