package io.github.qeroney.exceptionUtils.exceptions;

import io.github.qeroney.exceptionUtils.dto.MessageError;

public class BaseException extends RuntimeException {

    private int code;

    public int getCode() {
        return this.code;
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(MessageError error) {
        this(error.getCode(), error.getMessage());
    }
}
