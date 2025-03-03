package ru.fil.moneyFlow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private Integer amount;

    private LocalDate date;

    private Integer categoryId;
}
