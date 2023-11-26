package com.example.imports.validation.rentvalidation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@ConditionalOnProperty(value = "rent.validator", havingValue = "true")
public class RentStatusValidator implements RentValidator {

    @Override
    public String validate(Map<String, Object> parameters) {
        return Optional.ofNullable(parameters.get("status"))
                .map(String::valueOf)
                .filter(v -> !v.trim().isEmpty())
                .map(title -> "")
                .orElse("INVALID_STATUS");
    }

}
