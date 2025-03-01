package ru.fil.moneyFlow.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthExceptionResponse {

    private List<FieldErrorBody> errors;

    public void addError(FieldErrorBody error) {
        if(errors==null){
            errors = new ArrayList<>();
        }
        errors.add(error);
    }
}
