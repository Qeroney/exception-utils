package io.github.qeroney.exceptionUtils;

import io.github.qeroney.exceptionUtils.exceptions.ConflictException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import io.github.qeroney.exceptionUtils.dto.ErrorDto;

public abstract class BaseApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(BaseApiExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(buildErrorResponse(exception, 400, "Некорректные параметры запроса"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDto handleResourceNotFoundException(ConstraintViolationException exception) {
        return buildErrorResponse(exception, 400, "Неправильный формат параметра в запросе");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public ErrorDto processIllegalArgumentException(IllegalArgumentException exception) {
        return buildErrorResponse(exception, 400, "Неверные параметры запроса.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public ErrorDto processRuntimeException(RuntimeException exception) {
        return buildErrorResponse(exception, 0, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ErrorDto handleNoHandlerFoundException(NoHandlerFoundException exception) {
        return buildErrorResponse(exception, 404, "Запрашиваемый ресурс не найден");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ConflictException.class)
    @ResponseBody
    public ErrorDto handleConflictException(ConflictException exception) {
        return buildErrorResponse(exception, 404, exception.getMessage());
    }

    private ErrorDto buildErrorResponse(Throwable e, int code, String message) {
        log.error("Handle exception:", e);
        return new ErrorDto(code, message);
    }
}
