package com.example.demo.repository;

import com.example.demo.model.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Integer> {
    @Query("""
        SELECT b FROM Books b
        WHERE
            (:search IS NULL
                OR LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(b.author) LIKE LOWER(CONCAT('%', :search, '%'))
            )
        AND (:categoryId IS NULL OR b.categoryId = :categoryId)
        """)
    Page<Books> findBooks(
            @Param("search") String search,
            @Param("categoryId") Integer categoryId,
            Pageable pageable
    );

    @Query(
            value = """
                SELECT
                    MAX(b.price) AS max_price,
                    MIN(b.price) AS min_price,
                    AVG(b.price) AS avg_price
                FROM books b
            """,
            nativeQuery = true
    )
    List<Object[]> getPrices();
}
