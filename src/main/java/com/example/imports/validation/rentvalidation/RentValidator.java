package com.example.imports.validation.rentvalidation;

import java.util.Map;

public interface RentValidator {
    String validate(Map<String, Object> parameters);
}
