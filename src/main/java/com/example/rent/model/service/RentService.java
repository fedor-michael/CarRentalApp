package com.example.rent.model.service;

import com.example.rent.model.CreateRentCommand;
import com.example.rent.model.RentDto;
import com.example.rent.model.UpdateRentCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentService {

    Page<RentDto> findAll(Pageable pageable);

    RentDto findById(Long id);

    RentDto save(CreateRentCommand command);

    RentDto updateRent(UpdateRentCommand command);

    void deleteRent(Long id);

}
