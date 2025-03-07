package ru.fil.moneyFlow.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.fil.moneyFlow.dto.UserRequest;
import ru.fil.moneyFlow.dto.UserResponse;
import ru.fil.moneyFlow.models.User;
import ru.fil.moneyFlow.services.UserService;
import ru.fil.moneyFlow.utils.CompositeExceptionResponse;
import ru.fil.moneyFlow.utils.CompositeExceptionResponseGenerator;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AccountController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public ResponseEntity<UserResponse> getUser() {
        User userFromContext = getCurrentUser();
        int id=userFromContext.getId();
        User user=userService.getById(id).get();
        return ResponseEntity.ok(convertToUserResponse(user));
    }

    @PatchMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserRequest userRequest,
                                                   BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            CompositeExceptionResponse compositeExceptionResponse = CompositeExceptionResponseGenerator.generate(bindingResult);
            return new ResponseEntity(compositeExceptionResponse, HttpStatus.BAD_REQUEST);
        }

        User userFromContext = getCurrentUser();
        int id=userFromContext.getId();
        User updatedUser=userService.update(id, userRequest); id=updatedUser.getId();
        User user=userService.getById(id).get();
        return ResponseEntity.ok(convertToUserResponse(user));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(){
        User userFromContext = getCurrentUser();
        int id=userFromContext.getId();
        userService.delete(id);
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }

    private User getCurrentUser(){
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    private UserResponse convertToUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
}
