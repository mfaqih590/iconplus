package com.example.demo.service;

import com.example.demo.dto.RegisterUserRequest;
import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String login(String email, String password) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateToken(user);
    }

    public Users registerUser(RegisterUserRequest request) {
        Users users = new Users();
        users.setName(request.getName());
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered!");
        }
        users.setEmail(request.getEmail());
        if(!request.getRole().equalsIgnoreCase(USER) && !request.getRole().equalsIgnoreCase(ADMIN)) {
            throw new IllegalArgumentException("Role must be USER or ADMIN");
        }
        users.setRole(request.getRole());
        users.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(users);

        return users;
    }
}
