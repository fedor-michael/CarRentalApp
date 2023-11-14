package com.example.user.importvalidation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

@Service
@ConditionalOnProperty(value = "user.validator", havingValue = "true")
public class UserPhoneNumberValidator implements UserValidator {

    @Override
    public String validate(Map<String, Object> parameters) {
        return Optional.ofNullable(parameters.get("phonenumber"))
                .map(BigInteger.class::cast)
                .filter(v -> v.compareTo(BigInteger.ZERO) >= 0)
                .map(v -> "")
                .orElse("INVALID_PHONE_NUMBER");
    }

}
