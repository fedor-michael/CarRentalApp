package com.example.car;

import com.example.car.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query(value = "select a from Car a left join fetch a.isAvailable") //todo to dopracować bo nie bedzie działać chyba
    Page<Car> findAllFreeToRentCars(Pageable pageable);
}
