package com.gerenciamentomedico.exception.exceptions;

public class DataFormatException extends RuntimeException{

    public DataFormatException() {
        super("A data inserida é inválida.");
    }

    public DataFormatException(String message) {
        super(message);
    }

}
