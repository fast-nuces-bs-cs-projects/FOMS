package com.foms.foodieapi.controller;

import com.foms.foodieapi.model.User;
import com.foms.foodieapi.repository.UserRepository;
import com.foms.foodieapi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/auth", "/auth"}) // Supports both FXML (/auth/login) and Android (/api/auth/login)
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping({"/signup", "/register"}) // Supports both FXML (/auth/signup) and Android (/api/auth/register)
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already taken!");
        }

        // Create new user's account
        User user = new User();
        user.setName(signupRequest.getName() != null ? signupRequest.getName() : signupRequest.getFullName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole("CUSTOMER"); // Default role
        user.setStatus("Active");

        userRepository.save(user);

        // Auto-login and return JWT token
        final String jwt = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping({"/change-password"}) // Supports both FXML and Android because of class level RequestMapping
    public ResponseEntity<?> changePassword(org.springframework.security.core.Authentication authentication, @RequestBody ChangePasswordRequest request) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getCurrent_password(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Incorrect current password");
        }

        user.setPassword(passwordEncoder.encode(request.getNew_password()));
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }

    // --- DTOs ---

    public static class ChangePasswordRequest {
        private String current_password;
        private String new_password;

        public String getCurrent_password() { return current_password; }
        public void setCurrent_password(String current_password) { this.current_password = current_password; }
        public String getNew_password() { return new_password; }
        public void setNew_password(String new_password) { this.new_password = new_password; }
    }

    public static class LoginRequest {
        private String email;
        private String password;
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class SignupRequest {
        private String name;
        private String full_name; // From Android payload
        private String email;
        private String password;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getFullName() { return full_name; }
        public void setFullName(String full_name) { this.full_name = full_name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class AuthResponse {
        private final String token;
        
        public AuthResponse(String token) { this.token = token; }
        public String getToken() { return token; }
    }
}
