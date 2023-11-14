package com.example.rent.importvalidation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

@Service
@ConditionalOnProperty(value = "rent.validator", havingValue = "true")
public class RentCarValidator implements RentValidator {

    @Override
    public String validate(Map<String, Object> parameters) {
        return Optional.ofNullable(parameters.get("carid"))
                .map(BigInteger.class::cast)
                .filter(v -> v.compareTo(BigInteger.ZERO) >= 0)
                .map(v -> "")
                .orElse("INVALID_CAR_ID");
    }

}
