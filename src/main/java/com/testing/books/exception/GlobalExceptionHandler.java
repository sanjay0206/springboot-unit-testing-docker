package com.testing.books.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // handle resource specific exceptions
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorDetailsResponse> handleBookNotFoundException(BookNotFoundException exception,
                                                                            WebRequest webRequest) {
        ErrorDetailsResponse errorDetails = new ErrorDetailsResponse(LocalDateTime.now(), exception.getMessage(),
                webRequest.getDescription(false));

        log.info("ErrorDetailsResponse: " + errorDetails);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // handle global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsResponse> handleGlobalException(Exception exception,
                                                                      WebRequest webRequest) {
        ErrorDetailsResponse errorDetails = new ErrorDetailsResponse(LocalDateTime.now(), exception.getMessage(),
                webRequest.getDescription(false));

        log.info("ErrorDetailsResponse: " + errorDetails);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}