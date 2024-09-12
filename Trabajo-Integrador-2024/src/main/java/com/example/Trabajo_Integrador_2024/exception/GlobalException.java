package com.example.Trabajo_Integrador_2024.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> processResourceNotFountException(ResourceNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> processBadRequestException(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> processDuplicateResourceException(DuplicateResourceException exception) {
        //El código 409 indica que la solicitud no puede completarse debido a un conflicto
        // con el estado actual del recurso.
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> processConflictException(ConflictException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
}
