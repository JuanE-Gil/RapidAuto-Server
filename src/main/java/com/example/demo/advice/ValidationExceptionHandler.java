package com.example.demo.advice;


import com.example.demo.Service.RegistroExistenteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice//Le esta diciendo que con esto vamos a captar errores
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleInvalidArguments(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>(); //Creamos una extension del Map

        //Con el getBindignResultado jalamos el error
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage()); //Mandamos el mensaje
        });
        return errors; //Devolvemos el error

    }

    @ExceptionHandler(RegistroExistenteException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleRegistroExistenteException(RegistroExistenteException ex) {
        String mensaje = ex.getMessage(); // Obtener el mensaje de la excepción
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje); // Retornar el mensaje como respuesta JSON con estado 500 Internal Server Error
    }

}
