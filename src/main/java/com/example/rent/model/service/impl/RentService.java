package com.example.rent.model.service.impl;

import com.example.car.CarRepository;
import com.example.car.model.Car;
import com.example.exception.EntityNotFoundException;
import com.example.exception.UnsupportedOperationException;
import com.example.rent.RentRepository;
import com.example.rent.model.CreateRentCommand;
import com.example.rent.model.Rent;
import com.example.rent.model.RentDto;
import com.example.rent.model.RentStatus;
import com.example.rent.model.UpdateRentCommand;
import com.example.user.UserRepository;
import com.example.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentService implements com.example.rent.model.service.RentService {

    private final RentRepository rentRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<RentDto> findAll(Pageable pageable) {
        return rentRepository.findAll(pageable).map(RentDto::fromEntity);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public RentDto findById(Long id) {
        return rentRepository.findById(id)
                .map(RentDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(Rent.class.getSimpleName(), id));
    }

    //todo
    // find after date
    // find before date
    // find between dates

    //todo
    // findWithStatus

    //todo
    // findRentsWithCar

    //private Long id;
    //    private LocalDate dateFrom;
    //    private LocalDate dateTo;
    //    @Enumerated(EnumType.STRING)
    //    private RentStatus status;
    //    @ManyToOne
    //    @JoinColumn(name = "car_id") //todo tu może być zmiana przy postgreSQL
    //    private Car car;
    //    @ManyToOne
    //    @JoinColumn(name = "user_id") //todo tu może być zmiana przy postgreSQL
    //    private User user;
    //    private Integer startMileage;
    //    private Integer endMileage;
    //    private boolean deleted;

    public RentDto save(CreateRentCommand command) {
        Car carToUpdate = carRepository.findById(command.getCarId())
                .orElseThrow(() -> new EntityNotFoundException(Car.class.getSimpleName(), command.getCarId()));

        Rent rentToSave = Rent.builder()
                .dateFrom(command.getDateFrom())
                .dateTo(command.getDateTo())
                .status(RentStatus.valueOf(command.getStatus()))
                .car(carRepository.findById(command.getCarId())
                        .orElseThrow(() -> new EntityNotFoundException(Car.class.getSimpleName(), command.getCarId())))
                .user(userRepository.findById(command.getUserId())
                        .orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), command.getUserId())))
                .startMileage(carToUpdate.getMileage())
                .endMileage(command.getEndMileage())
                .build();
        if (command.getStatus().equals(RentStatus.ACTIVE.toString())) {

            if (!carToUpdate.getIsAvailable()) {
                throw new UnsupportedOperationException("CAR_NOT_AVAILABLE");
            }
            carToUpdate.setIsAvailable(false);
            carRepository.saveAndFlush(carToUpdate);
            return RentDto.fromEntity(rentRepository.save(rentToSave));
        } else {
            throw new UnsupportedOperationException("CANNOT_SAVE_WITH_STATUS_" + command.getStatus().toUpperCase());
        }

    }

    public RentDto updateRent(UpdateRentCommand command) {
        Car carToUpdate;
        if (command.getStatus().equals(RentStatus.ACTIVE.toString())) {
            carToUpdate = carRepository.findById(command.getCarId())
                    .map(car -> {
                        if (!car.getIsAvailable()) {
                            throw new UnsupportedOperationException("CAR_NOT_AVAILABLE");
                        }
                        return car;
                    })
                    .map(car -> {
                        car.setIsAvailable(false);
                        return car;
                    }).orElseThrow(() -> new EntityNotFoundException(Car.class.getSimpleName(), command.getCarId()));
            carRepository.saveAndFlush(carToUpdate);
        } else if (command.getStatus().equals(RentStatus.DONE.toString())) {
            carToUpdate = carRepository.findById(command.getCarId())
                    .map(car -> {
                        car.setMileage(command.getEndMileage());
                        car.setIsAvailable(true);
                        return car;
                    }).orElseThrow(() -> new EntityNotFoundException(Car.class.getSimpleName(), command.getCarId()));
            carRepository.saveAndFlush(carToUpdate);
        } else {
            throw new UnsupportedOperationException("CANNOT_SAVE_WITH_STATUS_" + command.getStatus().toUpperCase());
        }
        Rent rentToUpdate = rentRepository.findById(command.getId())
                .map(rent -> {
                    rent.setDateFrom(command.getDateFrom());
                    rent.setDateTo(command.getDateTo());
                    rent.setStatus(RentStatus.valueOf(command.getStatus()));
                    rent.setCar(carToUpdate);
                    rent.setUser(userRepository.findById(command.getUserId())
                            .orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), command.getUserId())));
                    rent.setStartMileage(command.getStartMileage());
                    rent.setEndMileage(command.getEndMileage());
                    return rent;
                }).orElseThrow(() -> new EntityNotFoundException(Rent.class.getSimpleName(), command.getId()));
        return RentDto.fromEntity(rentRepository.saveAndFlush(rentToUpdate));
    }

    public void deleteRent(Long id) {
        rentRepository.deleteById(id);
    }

}
