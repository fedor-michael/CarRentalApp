package com.example.car.importvalidation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@ConditionalOnProperty(value = "car.validator", havingValue = "true")
public class CarMileageValidator implements CarValidator {

    @Override
    public String validate(Map<String, Object> parameters) {
        return Optional.ofNullable(parameters.get("mileage"))
                .filter(v -> ((int) v) >= 0)
                .map(v -> "")
                .orElse("INVALID_MILEAGE");
    }

}
