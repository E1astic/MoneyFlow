package ru.fil.moneyFlow.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class CompositeExceptionResponseGenerator {
    public static CompositeExceptionResponse generate(BindingResult bindingResult) {
        CompositeExceptionResponse compositeExceptionResponse = new CompositeExceptionResponse();
        for(FieldError error : bindingResult.getFieldErrors()){
            FieldErrorBody fieldErrorBody=new FieldErrorBody(
                    error.getField(), error.getDefaultMessage());
            compositeExceptionResponse.addError(fieldErrorBody);
        }
        return compositeExceptionResponse;
    }
}
