package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.model.Books;
import com.example.demo.model.OrderItems;
import com.example.demo.model.Orders;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JWTService jwtService;

    private static final String PENDING = "PENDING";
    private static final String PAID = "PAID";
    private static final String CANCELLED = "CANCELLED";

    @Transactional
    public Orders createOrder(OrderRequest request, String token) {
        JwtUserInfo userInfo = jwtService.extractUserInfo(token);
        Orders orders = new Orders();
        orders.setUserId(userInfo.getId());
        orders.setStatus(PENDING);
        orders.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        Orders savedOrders = orderRepository.save(orders);
        Integer orderId = savedOrders.getId();
        double totalPrice = 0.0;
        for(OrderItemRequest itemRequest : request.getOrderItemRequestList()) {
            OrderItems orderItems = new OrderItems();
            Books books = bookRepository.findById(itemRequest.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));
            Integer Quantity = itemRequest.getQuantity();
            Integer Stock = books.getStock();
            Integer newStock = Stock - Quantity;
            books.setStock(newStock);
            bookRepository.save(books);
            if(Stock < Quantity) {
                throw new RuntimeException("Stock not available!");
            }
            orderItems.setQuantity(Quantity);
            orderItems.setBookId(itemRequest.getBookId());
            orderItems.setOrderId(orderId);
            orderItems.setPrice(books.getPrice());
            totalPrice += books.getPrice() * Quantity;
            orderItemRepository.save(orderItems);
        }
        savedOrders.setTotalPrice(BigDecimal.valueOf(totalPrice));
        orderRepository.save(savedOrders);

        return savedOrders;
    }

    public Orders payOrder(Integer orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order Not Found"));
        orders.setStatus(PAID);
        orderRepository.save(orders);

        return orders;
    }

    public List<OrderResponse> getDataOrders(String token) {
        JwtUserInfo userInfo = jwtService.extractUserInfo(token);
        String userRole = userInfo.getRole();
        Integer userId = userInfo.getId();
        List<Orders> orderList;
        if(userRole.equalsIgnoreCase("ADMIN")) {
            orderList = orderRepository.findAll();
        } else {
            orderList = orderRepository.findByUserId(userId);
        }
        List<OrderResponse> responses = new ArrayList<>();

        for (Orders order : orderList) {
            OrderResponse response = new OrderResponse();
            response.setId(order.getId());
            response.setUserId(order.getUserId());
            response.setStatus(order.getStatus());
            response.setTotalPrice(order.getTotalPrice());

            responses.add(response);
        }
        return responses;
    }

    public OrderResponse getDetailOrder(String token, Integer orderId) {
        JwtUserInfo userInfo = jwtService.extractUserInfo(token);
        String userRole = userInfo.getRole();
        Integer userId = userInfo.getId();
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order Not Found"));
        Integer userIdOrder = orders.getUserId();
        if(userRole.equalsIgnoreCase("USER")) {
            if(!userIdOrder.equals(userId)) {
                throw new RuntimeException("Invalid User!");
            }
        }

        return getOrderResponse(orders);
    }

    public OrderResponse getOrderResponse(Orders orders) {
        OrderResponse responses = new OrderResponse();
        responses.setId(orders.getId());
        responses.setUserId(orders.getUserId());
        responses.setStatus(orders.getStatus());
        responses.setTotalPrice(orders.getTotalPrice());
        responses.setCreatedAt(orders.getCreatedAt());
        responses.setOrderItemResponseList(getOrderItem(orders.getId()));

        return responses;
    }

    public List<OrderItemResponse> getOrderItem(Integer id) {
        List<OrderItems> orderItemsList = orderItemRepository.findByOrderId(id);
        List<OrderItemResponse> responses = new ArrayList<>();
        for(OrderItems items : orderItemsList) {
            OrderItemResponse response = new OrderItemResponse();
            response.setId(items.getId());
            response.setOrderId(items.getOrderId());
            response.setBookId(items.getBookId());
            Books books = bookRepository.findById(items.getBookId()).orElseThrow(() -> new RuntimeException("book not found"));
            response.setBookTitle(books.getTitle());
            response.setQuantity(items.getQuantity());
            response.setPrice(BigDecimal.valueOf(items.getPrice()));
            responses.add(response);
        }
        return responses;
    }
}
