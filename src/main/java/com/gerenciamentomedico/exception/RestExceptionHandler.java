package com.gerenciamentomedico.exception;


import com.gerenciamentomedico.exception.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorMessage> handleGeneralException(Exception ex) {
        RestErrorMessage error = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro inesperado.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.name());
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DataFormatException.class)
    private ResponseEntity<RestErrorMessage> DateTimeParseHandler(DataFormatException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, "A data inserida não é uma data válida");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(ConflictException.class)
    private ResponseEntity<RestErrorMessage> conflictHandler(ConflictException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<RestErrorMessage> noFoundHandler(NotFoundException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<RestErrorMessage> badRequestHandler(BadRequestException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<RestErrorMessage> notValidUUIDHandler(MethodArgumentTypeMismatchException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, "O valor inserido no campo ID não é um UUID");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(FieldNotFilledException.class)
    private ResponseEntity<RestErrorMessage> fieldNotFilledHandler(FieldNotFilledException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, "O campo não pode ser vazio");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidDateFormat(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.name());

        String errorMessage = ex.getMessage();

        if (errorMessage != null && errorMessage.contains("Failed to deserialize java.time.LocalDate")) {
            errors.put("message", "Formato de data inválido. Utilize o formato dd-MM-yyyy.");
        }
        if(errorMessage != null && errorMessage.contains("Cannot deserialize value of type `java.util.UUID`")) {
            errors.put("message", "O id inserido não é um UUID.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> duplicateHandler(DataIntegrityViolationException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("status", HttpStatus.CONFLICT.name());

        String errorMessage = ex.getMostSpecificCause().getMessage();

        if (errorMessage.contains("cpf")) {
            errors.put("message", "Já existe um cadastro com esse CPF.");
        }
        if (errorMessage.contains("contato")) {
            errors.put("message", "Já existe um cadastro com esse contato.");
        }
        if (errorMessage.contains("crm")) {
            errors.put("message", "Já existe um cadastro com esse CRM.");
        }
        if (errorMessage.contains("email")) {
            errors.put("message", "Já existe um cadastro com esse e-mail.");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }
}
