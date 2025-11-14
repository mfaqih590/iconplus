package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterUserRequest;
import com.example.demo.dto.Response;
import com.example.demo.model.Users;
import com.example.demo.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Validated @RequestBody RegisterUserRequest request) {
        Users users = authService.registerUser(request);

        return ResponseEntity.ok(
                new Response("SUCCESS", true, users.getName())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@Validated @RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());

        return ResponseEntity.ok(
                new Response("SUCCESS", true, new LoginResponse(token))
        );
    }
}
