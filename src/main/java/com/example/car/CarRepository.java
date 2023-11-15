package com.example.car;

import com.example.car.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT a FROM Car a WHERE a.productionYear > ?1")
    Page<Car> findAllNewerThan(int year, Pageable pageable);

    @Query(value = "select * from cars a where a.isAvailable = true", nativeQuery = true)
    Page<Car> findAllFreeToRent(Pageable pageable);

}
