package com.gerenciamentomedico.exception.exceptions;

public class ConflictException extends RuntimeException{

    public ConflictException() {
        super("O valor inserido no campo já está sendo utilizado por outro cadastro");
    }

    public ConflictException(String message) {
        super(message);
    }
}
