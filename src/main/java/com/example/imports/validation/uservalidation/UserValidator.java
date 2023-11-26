package com.example.imports.validation.uservalidation;

import java.util.Map;

public interface UserValidator {
    String validate(Map<String, Object> parameters);
}
