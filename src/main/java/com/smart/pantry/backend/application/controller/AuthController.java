package com.smart.pantry.backend.application.controller;

import com.smart.pantry.backend.application.dto.LoginRequest;
import com.smart.pantry.backend.application.dto.RegisterRequest;
import com.smart.pantry.backend.application.model.User;
import com.smart.pantry.backend.application.security.JwtTokenProvider;
import com.smart.pantry.backend.application.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager=authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        // The @Valid annotation triggers the validation rules on the RegisterRequest DTO.
        // If validation fails, a MethodArgumentNotValidException is thrown, which Spring Boot
        // handles by default, returning a 400 Bad Request response.

        // Register the user using the service layer
        User registeredUser = authService.registerUser(registerRequest);

        // On success, generate a JWT token for the new user
        String jwt = tokenProvider.generateToken(registeredUser);

        // Return the token in the response body with a 201 Created status
        // The response body matches the contract: { "token": "ey..." }
        return new ResponseEntity<>(Map.of("token", jwt), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken= tokenProvider.generateToken((User)authentication.getPrincipal());
        return ResponseEntity.ok(Map.of("token",jwtToken));
    }
}
