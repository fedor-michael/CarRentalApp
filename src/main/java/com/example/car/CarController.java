package com.example.car;

import com.example.car.model.CarDto;
import com.example.car.model.CreateCarCommand;
import com.example.car.model.UpdateCarCommand;
import com.example.car.service.impl.CarService;
import com.example.imports.model.ImportStatus;
import com.example.imports.model.ImportStatusDto;
import com.example.imports.service.impl.ImportService;
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
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final ImportService importService;

    @GetMapping
    public Page<CarDto> findAll(@PageableDefault Pageable pageable) {
        return carService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CarDto findById(@PathVariable Long id) {
        return carService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDto save(@Valid @RequestBody CreateCarCommand command) {
        return carService.save(command);
    }

    @PutMapping
    public CarDto update(@Valid @RequestBody UpdateCarCommand command) {
        return carService.updateCar(command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }

    @SneakyThrows
    @PostMapping("/_upload")
    public ResponseEntity uploadCsvFile(@RequestParam("file") MultipartFile file) {
        ImportStatusDto importStatusDto = importService.saveNewImport(file.getOriginalFilename(), ImportStatus.EntityType.CAR);
        importService.uploadCsvToDb(file.getBytes(), importStatusDto.id(), ImportStatus.EntityType.CAR);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(importStatusDto);
    }

    @GetMapping("/newer/{year}")
    public Page<CarDto> findNewerThan(@PageableDefault Pageable pageable, @PathVariable int year) {
        return carService.findNewerThan(pageable, year);
    }

    @GetMapping("/free")
    public Page<CarDto> findAllFreeToRent(@PageableDefault Pageable pageable) {
        return carService.findAllFreeToRent(pageable);
    }

}
