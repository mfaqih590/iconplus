package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Pattern(regexp = ".*@.*", message = "Email must contain '@'")
    private String email;
    @NotBlank(message = "Email is required")
    private String password;
    @NotBlank(message = "Role is required")
    private String role;
}
