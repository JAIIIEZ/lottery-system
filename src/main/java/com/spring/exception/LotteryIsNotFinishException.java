package com.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.TOO_EARLY)
public class LotteryIsNotFinishException extends Exception {

    private static final long serialVersionUID = 1L;

    public LotteryIsNotFinishException(String message) {
        super(message);
    }
}

