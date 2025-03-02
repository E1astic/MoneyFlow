package ru.fil.moneyFlow.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.fil.moneyFlow.services.AuthenticationService;
import ru.fil.moneyFlow.dto.AuthenticationRequest;
import ru.fil.moneyFlow.dto.AuthenticationResponse;
import ru.fil.moneyFlow.dto.RegisterRequest;
import ru.fil.moneyFlow.utils.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authService;
    private final RegisterValidator registerValidator;

    @Autowired
    public AuthController(AuthenticationService authenticationService, RegisterValidator registerValidator) {
        this.authService = authenticationService;
        this.registerValidator = registerValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterRequest registerRequest,
            BindingResult bindingResult) {

        registerValidator.validate(registerRequest, bindingResult);

        if(bindingResult.hasErrors()) {
            CompositeExceptionResponse authExceptionResponse = CompositeExceptionResponseGenerator.generate(bindingResult);
            return new ResponseEntity(authExceptionResponse, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    }


    @ExceptionHandler
    public ResponseEntity<SimpleExceptionResponse> handleAuthenticationException(AuthException e){
        SimpleExceptionResponse exceptionResponse=new SimpleExceptionResponse(e.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
