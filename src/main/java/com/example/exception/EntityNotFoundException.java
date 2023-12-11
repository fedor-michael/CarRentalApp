package com.example.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class EntityNotFoundException extends RuntimeException{
    String name;
    long id;



}
