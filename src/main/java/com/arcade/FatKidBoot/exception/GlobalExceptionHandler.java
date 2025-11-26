package com.arcade.FatKidBoot.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // For EntityNotFound
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    //A custom error message
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundHandler(UserNotFoundException uf) {
        ErrorMessage e = ErrorMessage.builder().status(HttpStatus.NOT_FOUND).message(uf.getMessage()).build();
        log.error("USER NOT FOUND {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e); //I will fix this
    }

}