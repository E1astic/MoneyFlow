package ru.fil.moneyFlow.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.fil.moneyFlow.dto.UserRequest;
import ru.fil.moneyFlow.dto.UserResponse;
import ru.fil.moneyFlow.models.User;
import ru.fil.moneyFlow.services.UserService;

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
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest) {
        User userFromContext = getCurrentUser();
        int id=userFromContext.getId();
        User updatedUser=userService.update(id, userRequest);
        return ResponseEntity.ok(convertToUserResponse(updatedUser));
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
