package ru.fil.moneyFlow.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldErrorBody {
    private String field;
    private String error;
}
