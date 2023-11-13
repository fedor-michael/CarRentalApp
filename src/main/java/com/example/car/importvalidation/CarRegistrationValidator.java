package com.example.car.importvalidation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@ConditionalOnProperty(value = "car.validator", havingValue = "true")
public class CarRegistrationValidator implements CarValidator {

    @Override
    public String validate(Map<String, Object> parameters) {
        return Optional.ofNullable(parameters.get("registration"))
                .map(String::valueOf)
                .filter(v -> !v.trim().isEmpty())
                .filter(v -> v.length() <= 100)
                .map(title -> "")
                .orElse("INVALID_REGISTRATION");
    }

}
