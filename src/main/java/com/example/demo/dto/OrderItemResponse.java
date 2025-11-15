package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    private Integer id;
    private Integer orderId;
    private Integer bookId;
    private String bookTitle;
    private Integer quantity;
    private BigDecimal price;
}
