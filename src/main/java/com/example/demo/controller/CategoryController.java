package com.example.demo.controller;

import com.example.demo.dto.CategoryRequest;
import com.example.demo.dto.CategoryResponse;
import com.example.demo.dto.RegisterUserRequest;
import com.example.demo.dto.Response;
import com.example.demo.model.Categories;
import com.example.demo.model.Users;
import com.example.demo.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping
    public ResponseEntity<Response> addData(@Validated @RequestBody CategoryRequest request) {
        Categories categories = categoryService.create(request);

        return ResponseEntity.ok(
                new Response("SUCCESS", true, categories.getName())
        );
    }

    @GetMapping
    public ResponseEntity<Response> getAllData() {
        List<Categories> categories = categoryService.getAll();

        return ResponseEntity.ok(
                new Response("SUCCESS", true, categories)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getData(@PathVariable Integer id) {
        CategoryResponse response = categoryService.getDetail(id);

        return ResponseEntity.ok(
                new Response("SUCCESS", true, response)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateData(@PathVariable Integer id,
                                               @Validated @RequestBody CategoryRequest request) {
        boolean updated = categoryService.update(id, request);

        return ResponseEntity.ok(
                new Response("SUCCESS", true, updated)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteData(@PathVariable Integer id) {
        boolean deleted = categoryService.deleteCategory(id);

        return ResponseEntity.ok(
                new Response("SUCCESS", true, deleted)
        );
    }

}
