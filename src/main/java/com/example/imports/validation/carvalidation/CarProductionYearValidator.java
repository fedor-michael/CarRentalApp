package com.example.imports.validation.carvalidation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@ConditionalOnProperty(value = "car.validator", havingValue = "true")
public class CarProductionYearValidator implements CarValidator {
    @Override
    public String validate(Map<String, Object> parameters) {
        return Optional.ofNullable(parameters.get("productionYear"))
                .filter(v -> ((int) v) >= 1886)
                .map(v -> "")
                .orElse("INVALID_PRODUCTION_YEAR");
    }

}
