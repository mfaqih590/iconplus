package com.example.demo.repository;

import com.example.demo.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByUserId(Integer userId);

    @Query(
            value = """
                SELECT
                    SUM(o.total_price) AS total_sold
                FROM orders o
            """,
            nativeQuery = true
    )
    BigDecimal getTotalRevenue();
}
