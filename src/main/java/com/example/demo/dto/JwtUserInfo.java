package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtUserInfo {
    private Integer id;
    private String name;
    private String role;
    private String email;
}
