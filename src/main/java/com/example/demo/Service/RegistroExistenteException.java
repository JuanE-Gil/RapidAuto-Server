package com.example.demo.Service;

import org.springframework.stereotype.Service;

public class RegistroExistenteException extends RuntimeException{
    public RegistroExistenteException() {
        super("Username en Uso");
    }

    public RegistroExistenteException(String message) {
        super(message);
    }
}
