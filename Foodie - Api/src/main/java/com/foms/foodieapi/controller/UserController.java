package com.foms.foodieapi.controller;

import com.foms.foodieapi.model.User;
import com.foms.foodieapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email).map(user -> ResponseEntity.ok(Map.of(
                "full_name", user.getName(),
                "email", user.getEmail(),
                "phone", user.getPhone() != null ? user.getPhone() : "",
                "profile_image_url", user.getProfileImageUrl() != null ? user.getProfileImageUrl() : ""
        ))).orElse(ResponseEntity.notFound().build());
    }
}
