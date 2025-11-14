package com.example.demo.service;

import com.example.demo.dto.BookRequest;
import com.example.demo.dto.BookResponse;
import com.example.demo.dto.CategoryRequest;
import com.example.demo.dto.CategoryResponse;
import com.example.demo.model.Books;
import com.example.demo.model.Categories;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Books createBook(BookRequest request) {

        Categories category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Books book = new Books();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPrice(request.getPrice());
        book.setStock(request.getStock());
        book.setYear(request.getYear());
        book.setImageBase64(request.getImageBase64());
        book.setCategoryId(category.getId());

        return bookRepository.save(book);
    }

    public Page<Books> getBooks(String search, Integer categoryId, Pageable pageable) {
        return bookRepository.findBooks(search, categoryId, pageable);
    }

    public BookResponse getDetail(Integer bookId) {
        Books books =  bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("data not found"));
        BookResponse response = new BookResponse();
        response.setId(books.getId());
        response.setTitle(books.getTitle());
        response.setPrice(books.getPrice());
        response.setYear(books.getYear());
        response.setAuthor(books.getAuthor());
        response.setStock(books.getStock());
        response.setImageBase64(books.getImageBase64());
        response.setCategoryId(books.getCategoryId());

        Categories categories = categoryRepository.findById(books.getCategoryId()).orElseThrow(() -> new RuntimeException("data not found"));
        String categoryName = categories.getName();
        response.setCategoryName(categoryName);

        return response;
    }

    public boolean deleteBook(Integer bookId) {
        Books books = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("data not found"));

        bookRepository.delete(books);

        return true;
    }

    public BookRequest update(Integer bookId, BookRequest request) {
        Books books = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("data not found"));

        books.setTitle(request.getTitle());
        books.setPrice(request.getPrice());
        books.setYear(request.getYear());
        books.setAuthor(request.getAuthor());
        books.setStock(request.getStock());
        books.setImageBase64(request.getImageBase64());
        Categories category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        books.setCategoryId(category.getId());
        bookRepository.save(books);

        return request;
    }
}
