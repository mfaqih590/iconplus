package com.example.demo.controller;

import com.example.demo.dto.CategoryRequest;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;
import com.example.demo.dto.Response;
import com.example.demo.model.Categories;
import com.example.demo.model.Orders;
import com.example.demo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/orders")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Response> addData(@Validated @RequestBody OrderRequest request, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        Orders orders = orderService.createOrder(request, token);

        return ResponseEntity.ok(
                new Response("SUCCESS", true, orders.getId())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> addData(@PathVariable Integer id) {
        Orders orders = orderService.payOrder(id);

        return ResponseEntity.ok(
                new Response("SUCCESS", true, orders.getId())
        );
    }

    @GetMapping
    public ResponseEntity<Response> getData(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        List<OrderResponse> responses = orderService.getDataOrders(token);

        return ResponseEntity.ok(
                new Response("SUCCESS", true, responses)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getDetail(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        String token = authHeader.replace("Bearer ", "").trim();
        OrderResponse response = orderService.getDetailOrder(token, id);

        return ResponseEntity.ok(
                new Response("SUCCESS", true, response)
        );
    }
}
