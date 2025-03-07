package ru.fil.moneyFlow.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    @Min(value = 1, message = "Amount should be greater than 0")
    @NotNull(message = "Amount should not be empty")
    private Integer amount;

    @NotNull(message = "Date should not be empty")
    private LocalDate date;

    @NotNull(message = "Date should not be empty")
    private Integer categoryId;
}
