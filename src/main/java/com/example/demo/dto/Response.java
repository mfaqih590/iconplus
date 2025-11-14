package com.example.demo.dto;

import lombok.Data;

@Data
public class Response {
    private String message;
    private Boolean status;
    private Object result;
    private Integer page;
    private Long size;
    private Long total;
}
