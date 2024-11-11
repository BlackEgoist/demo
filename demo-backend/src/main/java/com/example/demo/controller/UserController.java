package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponce;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping
    public UserResponce registerUser(@RequestBody UserRequest userRequest) throws UserAlreadyExistsException {
        return userService.createUser(userRequest.username(), userRequest.password());
    }

    @CrossOrigin
    @GetMapping("/me")
    public UserResponce currentUserInfo() {
        return userService.getCurrentUser();
    }
}
