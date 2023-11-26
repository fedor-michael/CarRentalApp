package com.example.imports.validation.rentvalidation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@ConditionalOnProperty(value = "rent.validator", havingValue = "true")
public class RentDateToValidator implements RentValidator {

    @Override
    public String validate(Map<String, Object> parameters) {
        return Optional.ofNullable(parameters.get("dateto"))
                .map(String::valueOf)
                .filter(v -> v.matches("^\\d{4}-\\d{2}-\\d{2}$"))
                .map(title -> "")
                .orElse("INVALID_DATE_TO");
    }

}
