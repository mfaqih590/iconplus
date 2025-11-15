package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Integer id;
    private Integer userId;
    private BigDecimal totalPrice;
    private String status;
    private Timestamp createdAt;
    private List<OrderItemResponse> orderItemResponseList;
}
