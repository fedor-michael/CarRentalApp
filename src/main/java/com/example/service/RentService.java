package com.example.service;

import com.example.model.rent.CreateRentCommand;
import com.example.model.rent.RentDto;
import com.example.model.rent.UpdateRentCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentService {

    Page<RentDto> findAll(Pageable pageable);

    RentDto findById(Long id);

    RentDto save(CreateRentCommand command);

    RentDto updateRent(UpdateRentCommand command);

    void deleteRent(Long id);

}
