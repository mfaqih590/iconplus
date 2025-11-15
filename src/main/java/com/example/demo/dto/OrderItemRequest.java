package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemRequest {
    private Integer id;
    private Integer orderId;
    private Integer bookId;
    private Integer quantity;
    private BigDecimal price;
}
