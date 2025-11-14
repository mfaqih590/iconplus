package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponsePageable {
    private boolean status;
    private String message;
    private Object result;
    private Integer page;
    private Integer size;
}
