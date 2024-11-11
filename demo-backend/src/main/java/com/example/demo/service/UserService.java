package com.example.demo.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserResponce;
import com.example.demo.entity.User;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // creates anew user if it doesn't already exist
    public UserResponce createUser(String username, String password) throws UserAlreadyExistsException {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException();
        }

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        userRepository.save(user);
        return new UserResponce(username);
    }

    // checks if the user entered valid credentials on login
    public UserResponce getCurrentUser() {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User authenticatedUser = (org.springframework.security.core.userdetails.User) currentAuth
                .getPrincipal();

        return userRepository.findByUsername(authenticatedUser.getUsername())
                .map(user -> new UserResponce(user.getUsername()))
                .get();
    }

}
