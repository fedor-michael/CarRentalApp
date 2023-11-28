package com.example.controller;

import com.example.service.impl.ImportService;
import com.example.model.importstatus.ImportStatus;
import com.example.model.importstatus.ImportStatusDto;
import com.example.model.rent.CreateRentCommand;
import com.example.model.rent.RentDto;
import com.example.model.rent.UpdateRentCommand;
import com.example.service.impl.RentService;
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
@RequestMapping("/api/v1/rents")
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;
    private final ImportService importService;

    @GetMapping
    public Page<RentDto> findAll(@PageableDefault Pageable pageable) {
        return rentService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public RentDto findById(@PathVariable Long id) {
        return rentService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RentDto save(@Valid @RequestBody CreateRentCommand command) {
        return rentService.save(command);
    }

    @PutMapping
    public RentDto update(@Valid @RequestBody UpdateRentCommand command) {
        return rentService.updateRent(command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRent(@PathVariable Long id) {
        rentService.deleteRent(id);
    }

    @SneakyThrows
    @PostMapping("/_upload")
    public ResponseEntity uploadCsvFile(@RequestParam("file") MultipartFile file) {
        ImportStatusDto importStatusDto = importService.saveNewImport(file.getOriginalFilename(), ImportStatus.EntityType.RENT);
        importService.uploadCsvToDb(file.getBytes(), importStatusDto.id(), ImportStatus.EntityType.RENT);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(importStatusDto);
    }

}
