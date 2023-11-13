package com.example.user.importvalidation;

import com.example.rent.importvalidation.RentValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
@ConditionalOnProperty(value = "user.validator", havingValue = "true")
public class UserPhoneNumberValidator implements RentValidator {

    @Override
    public String validate(Map<String, Object> parameters) {
        return Optional.ofNullable(parameters.get("phonenumber"))
                .map(BigDecimal.class::cast)
                .filter(v -> v.compareTo(BigDecimal.ZERO) >= 0)
                .map(v -> "")
                .orElse("INVALID_PHONE_NUMBER");
    }

}
