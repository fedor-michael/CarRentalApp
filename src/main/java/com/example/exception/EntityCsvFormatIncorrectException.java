package com.example.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class EntityCsvFormatIncorrectException extends RuntimeException{
    String name;
    String issue;
}
