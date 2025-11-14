package com.example.demo.service;

import com.example.demo.dto.CategoryRequest;
import com.example.demo.dto.CategoryResponse;
import com.example.demo.model.Categories;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Categories create(CategoryRequest request) {
        Categories categories = new Categories();
        categories.setName(request.getName());
        categoryRepository.save(categories);

        return categories;
    }

    public List<Categories> getAll() {
        return categoryRepository.findAll();
    }

    public CategoryResponse getDetail(Integer categoryId) {
        Categories categories = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("data not found"));

        CategoryResponse response = new CategoryResponse();
        response.setId(categories.getId());
        response.setName(categories.getName());

        return response;
    }

    public boolean update(Integer categoryId, CategoryRequest request) {
        Categories categories = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("data not found"));

        categories.setName(request.getName());
        categoryRepository.save(categories);

        return true;
    }

    public boolean deleteCategory(Integer categoryId) {
        Categories categories = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("data not found"));

        categoryRepository.delete(categories);

        return true;
    }
}
