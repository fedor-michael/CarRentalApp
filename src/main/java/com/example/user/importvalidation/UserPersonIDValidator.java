package com.example.user.importvalidation;

import com.example.rent.importvalidation.RentValidator;
import com.example.user.UserRepository;
import com.example.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "user.validator", havingValue = "true")
public class UserPersonIDValidator implements RentValidator {

    private final UserRepository userRepository;

    @Override
    public String validate(Map<String, Object> parameters) {
        return Optional.ofNullable(parameters.get("personid"))
                .map(String::valueOf)
                .filter(v -> !v.trim().isEmpty())
                .filter(v -> v.length() <= 100)
                .filter(this::isPersonIDUnique)
                .map(title -> "")
                .orElse("INVALID_PERSON_ID");
    }

    private boolean isPersonIDUnique(String personId) {
        return userRepository.findAll()
                .stream()
                .map(User::getPersonId)
                .filter(v -> v.equals(personId))
                .findAny()
                .isEmpty();
    }

}
