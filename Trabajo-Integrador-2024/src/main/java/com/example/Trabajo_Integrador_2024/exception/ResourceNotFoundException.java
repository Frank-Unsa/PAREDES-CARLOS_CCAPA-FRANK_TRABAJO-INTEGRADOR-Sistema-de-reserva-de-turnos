package com.example.Trabajo_Integrador_2024.exception;
// creamos nuestra propia excepcion
public class ResourceNotFoundException extends RuntimeException {
    //creo un constructor
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
