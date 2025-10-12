package com.smart.pantry.backend.application.controller;

import com.smart.pantry.backend.application.dto.ChangePasswordRequest;
import com.smart.pantry.backend.application.dto.UserUpdateRequest;
import com.smart.pantry.backend.application.model.User;
import com.smart.pantry.backend.application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Principal principal){
        User user=userService.getUserByEmail(principal.getName());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateUser(Principal principal,@Valid @RequestBody UserUpdateRequest userUpdateRequest){
        User updatedUser=userService.updateUser(principal.getName(),userUpdateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/me")
    public ResponseEntity<Void> changePassword(Principal principal,@Valid @RequestBody ChangePasswordRequest changePasswordRequest){
        userService.changePassword(principal.getName(),changePasswordRequest);
        return ResponseEntity.ok().build();
    }
}
