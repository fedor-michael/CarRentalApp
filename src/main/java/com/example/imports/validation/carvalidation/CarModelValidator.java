package com.example.imports.validation.carvalidation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@ConditionalOnProperty(value = "car.validator", havingValue = "true")
public class CarModelValidator implements CarValidator {

    @Override
    public String validate(Map<String, Object> parameters) {
        return Optional.ofNullable(parameters.get("model"))
                .map(String::valueOf)
                .filter(v -> !v.trim().isEmpty())
                .filter(v -> v.length() <= 100)
                .map(title -> "")
                .orElse("INVALID_MODEL");
    }

}
