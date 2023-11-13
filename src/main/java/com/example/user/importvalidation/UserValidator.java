package com.example.user.importvalidation;

import java.util.Map;

public interface UserValidator {
    String validate(Map<String, Object> parameters);
}
