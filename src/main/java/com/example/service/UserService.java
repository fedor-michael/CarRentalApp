package com.example.service;

import com.example.model.user.CreateUserCommand;
import com.example.model.user.UpdateUserCommand;
import com.example.model.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserDto> findAll(Pageable pageable);

    UserDto findById(Long id);

    UserDto save(CreateUserCommand command);

    UserDto updateUser(UpdateUserCommand command);

    void deleteUser(Long id);

}
