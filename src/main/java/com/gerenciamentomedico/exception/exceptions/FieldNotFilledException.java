package com.gerenciamentomedico.exception.exceptions;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.resource.NoResourceFoundException;

public class FieldNotFilledException extends NoResourceFoundException {

    public FieldNotFilledException(HttpMethod httpMethod, String resourcePath) {
        super(httpMethod, resourcePath);
    }
}
