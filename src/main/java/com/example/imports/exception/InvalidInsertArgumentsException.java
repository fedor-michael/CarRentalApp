package com.example.imports.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class InvalidInsertArgumentsException extends RuntimeException{
    String errors;
}
