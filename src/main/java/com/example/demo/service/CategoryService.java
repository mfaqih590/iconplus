package com.example.demo.service;

import com.example.demo.dto.CategoryRequest;
import com.example.demo.model.Categories;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
