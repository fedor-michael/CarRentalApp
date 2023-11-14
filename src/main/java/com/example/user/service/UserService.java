package com.example.user.service;

import com.example.user.model.CreateUserCommand;
import com.example.user.model.UpdateUserCommand;
import com.example.user.model.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserDto> findAll(Pageable pageable);

    UserDto findById(Long id);

    UserDto save(CreateUserCommand command);

    UserDto updateUser(UpdateUserCommand command);

    void deleteUser(Long id);

}
