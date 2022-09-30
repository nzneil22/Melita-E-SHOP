package com.melita_task.api.controllers;

import com.melita_task.api.exceptions.LogicalError;
import com.melita_task.api.exceptions.LogicalErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@Slf4j
@ControllerAdvice
public class OrderTakingControllerAdvice {

    @ExceptionHandler(LogicalErrorException.class)
    public ResponseEntity<LogicalError> handleLogicalErrorException(final LogicalErrorException ex) {

        return ResponseEntity.unprocessableEntity()
                .body(new LogicalError(ex.getCode()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleAuthException(final BadCredentialsException ex){
        log.error("Authentication Exception Occurred", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Void> handleDeniedException(final AccessDeniedException ex){
        log.error("Access Denied Exception Occurred", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleNotFoundException(final EntityNotFoundException ex) {
        log.error("Entity Not Found Exception Occurred", ex);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Void> handleException(final Throwable ex) {

        log.error("General Error Occurred", ex);
        return ResponseEntity.internalServerError()
                .build();
    }

}

