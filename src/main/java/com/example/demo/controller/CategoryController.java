package com.example.demo.controller;

import com.example.demo.dto.CategoryRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping
    public ResponseEntity<Response> addData(@Validated @RequestBody CategoryRequest request) {
        Response res = new Response();
        try {
            Categories categories =  categoryService.create(request);

            res.setStatus(true);
            res.setMessage("SUCCESS");
            res.setResult(categories.getName());
            return ResponseEntity.ok().body(res);
        } catch(Exception e){
            log.error(e.getMessage());
            res.setStatus(false);
            res.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
}
