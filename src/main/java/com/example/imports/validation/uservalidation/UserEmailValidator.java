package com.example.imports.validation.uservalidation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@ConditionalOnProperty(value = "user.validator", havingValue = "true")
public class UserEmailValidator implements UserValidator {

    @Override
    public String validate(Map<String, Object> parameters) {
        return Optional.ofNullable(parameters.get("email"))
                .map(String::valueOf)
                .filter(v -> v.matches(".+@.+"))
                .map(title -> "")
                .orElse("INVALID_EMAIL");
    }

}
