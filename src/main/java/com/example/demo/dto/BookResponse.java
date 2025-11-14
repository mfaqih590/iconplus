package com.example.demo.dto;

import lombok.Data;

@Data
public class BookResponse {
    private Integer id;
    private Integer categoryId;
    private String categoryName;
    private String title;
    private String author;
    private Double price;
    private Integer stock;
    private Integer year;
    private String imageBase64;
}
