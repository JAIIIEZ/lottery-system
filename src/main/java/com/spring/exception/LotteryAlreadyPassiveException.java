package com.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class LotteryAlreadyPassiveException extends Exception {

    private static final long serialVersionUID = 1L;

    public LotteryAlreadyPassiveException(String message) {
        super(message);
    }
}
