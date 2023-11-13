package com.example.car.importvalidation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
@ConditionalOnProperty(value = "car.validator", havingValue = "true")
public class CarMileageValidator implements CarValidator {

    @Override
    public String validate(Map<String, Object> parameters) {
        return Optional.ofNullable(parameters.get("mileage"))
                .map(BigDecimal.class::cast)
                .filter(v -> v.compareTo(BigDecimal.ONE) >= 0)
                .map(v -> "")
                .orElse("INVALID_MILEAGE");
    }

}
