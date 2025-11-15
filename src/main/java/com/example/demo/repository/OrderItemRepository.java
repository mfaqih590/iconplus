package com.example.demo.repository;

import com.example.demo.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Integer> {
    List<OrderItems> findByOrderId(Integer orderId);
    @Query(
            value = """
                SELECT
                    b.title AS title,
                    b.author AS author,
                    SUM(oi.quantity) AS total_sold
                FROM order_items oi
                JOIN books b ON b.id = oi.book_id
                GROUP BY b.id, b.title, b.author
                ORDER BY total_sold DESC
                LIMIT 3
            """,
            nativeQuery = true
    )
    List<Object[]> findTop3BestSeller();

    @Query(
            value = """
                SELECT
                    SUM(oi.quantity) AS total_sold
                FROM order_items oi
            """,
            nativeQuery = true
    )
    Long getTotalQuantity();
}
