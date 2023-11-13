package com.example.rent.importvalidation;

import java.util.Map;

public interface RentValidator {
    String validate(Map<String, Object> parameters);
}
