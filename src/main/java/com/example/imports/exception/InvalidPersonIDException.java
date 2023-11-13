package com.example.imports.exception;

import lombok.Value;

@Value
public class InvalidPersonIDException extends RuntimeException{
    String personId;
}
