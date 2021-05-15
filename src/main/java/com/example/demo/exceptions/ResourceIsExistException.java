package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The entity already exists")
public class ResourceIsExistException extends RuntimeException {
    public ResourceIsExistException(String message) { super(message); }
}
