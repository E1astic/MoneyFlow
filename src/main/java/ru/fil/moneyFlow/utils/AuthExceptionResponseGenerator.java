package ru.fil.moneyFlow.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class AuthExceptionResponseGenerator {
    public static AuthExceptionResponse generate(BindingResult bindingResult) {
        AuthExceptionResponse authExceptionResponse = new AuthExceptionResponse();
        for(FieldError error : bindingResult.getFieldErrors()){
            FieldErrorBody fieldErrorBody=new FieldErrorBody(
                    error.getField(), error.getDefaultMessage());
            authExceptionResponse.addError(fieldErrorBody);
        }
        return authExceptionResponse;
    }
}
