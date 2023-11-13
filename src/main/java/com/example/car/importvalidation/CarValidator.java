package com.example.car.importvalidation;

import java.util.Map;

public interface CarValidator {
    String validate(Map<String, Object> parameters);
}
