package com.example.imports.validation.uservalidation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@ConditionalOnProperty(value = "user.validator", havingValue = "true")
public class UserSurnameValidator implements UserValidator {

    @Override
    public String validate(Map<String, Object> parameters) {
        return Optional.ofNullable(parameters.get("surname"))
                .map(String::valueOf)
                .filter(v -> !v.trim().isEmpty())
                .filter(v -> v.length() <= 100)
                .map(title -> "")
                .orElse("INVALID_SURNAME");
    }

}
