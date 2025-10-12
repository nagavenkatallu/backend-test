package com.smart.pantry.backend.application.service;

import com.smart.pantry.backend.application.dto.RegisterRequest;
import com.smart.pantry.backend.application.exception.UserAlreadyExistsException;
import com.smart.pantry.backend.application.model.User;
import com.smart.pantry.backend.application.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Dependency Injection via constructor
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterRequest registerRequest) {
        // Mini-Task 2.2: Check for Existing User
        userRepository.findByEmail(registerRequest.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistsException("An account with this email already exists: " + user.getEmail());
        });

        // Mini-Task 2.2: Hash the Password
        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());

        // Mini-Task 2.2: Create and Save User
        User newUser = new User();
        newUser.setFirstName(registerRequest.getFirstName());
        newUser.setLastName(registerRequest.getLastName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPhoneNumber(registerRequest.getPhoneNumber()); // Can be null
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        return userRepository.save(newUser);
    }
}
