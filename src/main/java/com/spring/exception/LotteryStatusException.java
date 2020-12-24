package com.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class LotteryStatusException extends Exception {

    private static final long serialVersionUID = 1L;

    public LotteryStatusException(String message) {
        super(message);
    }
}
