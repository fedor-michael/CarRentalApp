package com.example.imports.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class InvalidPersonIDException extends RuntimeException{
    String personId;
}
