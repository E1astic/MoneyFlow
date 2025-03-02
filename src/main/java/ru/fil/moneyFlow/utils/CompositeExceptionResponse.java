package ru.fil.moneyFlow.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompositeExceptionResponse {

    private List<FieldErrorBody> errors;

    public void addError(FieldErrorBody error) {
        if(errors==null){
            errors = new ArrayList<>();
        }
        errors.add(error);
    }
}
