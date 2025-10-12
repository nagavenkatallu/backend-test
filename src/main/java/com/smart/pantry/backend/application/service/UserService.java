package com.smart.pantry.backend.application.service;

import com.smart.pantry.backend.application.dto.ChangePasswordRequest;
import com.smart.pantry.backend.application.dto.UserUpdateRequest;
import com.smart.pantry.backend.application.model.User;
import com.smart.pantry.backend.application.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public User updateUser(String email, UserUpdateRequest userUpdateRequest){
        User user= getUserByEmail(email);
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        return userRepository.save(user);
    }

    public void changePassword(String email, ChangePasswordRequest changePasswordRequest){
        User user= getUserByEmail(email);
        if(!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(),user.getPassword())){
            throw new IllegalStateException("Incorrect current password");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
