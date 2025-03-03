package ru.fil.moneyFlow.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotEmpty(message = "Firstname should not be empty")
    @Size(min = 2, max = 30, message = "Length of firstname should be between 2 and 30 characters")
    private String firstname;

    @NotEmpty(message = "Lastname should not be empty")
    @Size(min = 2, max = 30, message = "Length of lastname should be between 2 and 30 characters")
    private String lastname;

    @NotEmpty(message = "Firstname should not be empty")
    @Size(min = 4, message = "Length of password should be greater than 4 characters")
    private String password;

}
