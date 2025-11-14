package com.example.demo.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class BookRequest {
    private Integer categoryId;
    private String title;
    private String author;
    private Double price;
    private Integer stock;
    private Integer year;
    private String imageBase64;
}
