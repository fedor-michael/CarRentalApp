package com.example.controller;

import com.example.imports.service.impl.ImportService;
import com.example.imports.model.ImportStatus;
import com.example.imports.model.ImportStatusDto;
import com.example.model.user.CreateUserCommand;
import com.example.model.user.UpdateUserCommand;
import com.example.model.user.UserDto;
import com.example.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ImportService importService;

    @GetMapping
    public Page<UserDto> findAll(@PageableDefault Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto save(@Valid @RequestBody CreateUserCommand command) {
        return userService.save(command);
    }

    @PutMapping
    public UserDto update(@Valid @RequestBody UpdateUserCommand command) {
        return userService.updateUser(command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @SneakyThrows
    @PostMapping("/_upload")
    public ResponseEntity uploadCsvFile(@RequestParam("file") MultipartFile file) {
        ImportStatusDto importStatusDto = importService.saveNewImport(file.getOriginalFilename(), ImportStatus.EntityType.USER);
        importService.uploadCsvToDb(file.getBytes(), importStatusDto.id(), ImportStatus.EntityType.USER);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(importStatusDto);
    }

}
