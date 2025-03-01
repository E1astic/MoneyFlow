package ru.fil.moneyFlow.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.fil.moneyFlow.dto.RegisterRequest;
import ru.fil.moneyFlow.services.UserService;

@Component
public class RegisterValidator implements Validator {

    private final UserService userService;

    @Autowired
    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(RegisterRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterRequest registerRequest = (RegisterRequest) target;
        if(userService.getByEmail(registerRequest.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email has already been registered");
        }
    }
}
