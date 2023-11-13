package com.example.imports.exception;

import lombok.Value;

@Value
public class InvalidInsertArgumentsException extends RuntimeException{
    String errors;
}
