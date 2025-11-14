package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.Books;
import com.example.demo.model.Categories;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/books")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Response> addData(@Validated @RequestBody BookRequest request) {
        Books books = bookService.createBook(request);
        Response res = new Response("SUCCESS", true, books.getTitle());
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<ResponsePageable> getBooks(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Books> res = bookService.getBooks(search, categoryId, pageable);

        ResponsePageable response = new ResponsePageable(
                true,
                "SUCCESS",
                res.getContent(),
                page,
                size
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getData(@PathVariable Integer id) {
        BookResponse response = bookService.getDetail(id);

        return ResponseEntity.ok(
                new Response("SUCCESS", true, response)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateData(@PathVariable Integer id, @RequestBody BookRequest request) {
        BookRequest res = bookService.update(id, request);

        return ResponseEntity.ok(
                new Response("SUCCESS", true, res)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteData(@PathVariable Integer id) {
        boolean deleted = bookService.deleteBook(id);

        return ResponseEntity.ok(
                new Response("SUCCESS", true,deleted)
        );
    }
}
