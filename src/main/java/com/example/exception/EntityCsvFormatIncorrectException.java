package com.example.exception;

import lombok.Value;

@Value
public class EntityCsvFormatIncorrectException extends RuntimeException{
    String name;
    String issue;
}
