package com.manage.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NameAlreadyExistsException extends RuntimeException{
    private String message;
    public NameAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

}
