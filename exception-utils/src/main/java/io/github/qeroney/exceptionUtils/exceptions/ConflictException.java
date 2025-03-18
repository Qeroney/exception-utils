package io.github.qeroney.exceptionUtils.exceptions;

import io.github.qeroney.exceptionUtils.dto.MessageError;

public class ConflictException extends BaseException {

    public ConflictException(int code, String message) {
        super(code, message);
    }

    public ConflictException(MessageError error) {
        super(error);
    }
}
