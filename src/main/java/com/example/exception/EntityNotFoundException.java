package com.example.exception;

import lombok.Value;

@Value
public class EntityNotFoundException extends RuntimeException{
    String name;
    long id;



}
