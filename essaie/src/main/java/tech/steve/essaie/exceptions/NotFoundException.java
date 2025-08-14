package com.porc.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final Object details;

    public NotFoundException(String message) {
        super(message);
        this.details = null;
    }

    public NotFoundException(String message, Object details) {
        super(message);
        this.details = details;
    }

}

